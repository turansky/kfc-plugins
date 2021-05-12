// WA for https://github.com/Kotlin/kotlinx.serialization/issues/1449

package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

private val VALUE_CLASS_RETURN = Regex("""return (\w+)_init\(bitMask0, local0, null\);""")

internal class ValueClassSerializationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        if (jsIrCompiler)
            return@with

        plugins.withId(KotlinPlugin.SERIALIZATION) {
            tasks.withType<Kotlin2JsCompile> {
                doLast {
                    // IR check (both)
                    if (outputFile.isDirectory)
                        return@doLast

                    val content = outputFile.readText()
                    val newContent = applySerializationFixes(content)
                    if (newContent != content) {
                        outputFile.writeText(newContent)
                    }
                }
            }
        }
    }
}

private fun applySerializationFixes(
    source: String
): String {
    val classNames = VALUE_CLASS_RETURN.findAll(source)
        .map { it.groupValues[1] }
        .filter { "\n  function ${it}_init(" !in source }
        .toSet()

    if (classNames.isEmpty())
        return source

    var result = source
    for (className in classNames) {
        result = result.replaceMethodBody(className, "serialize") {
            val value = it.substringAfter(" 0, ").substringBefore(");")
            "    encoder.${it.encodeMethodName()}($value);"
        }

        result = result.replaceMethodBody(className, "deserialize") {
            "    return new $className(decoder.${it.decodeMethodName()}());"
        }
    }

    return result
}

private fun String.replaceMethodBody(
    className: String,
    methodName: String,
    transform: (String) -> String
): String {
    val start = "\n  $className${'$'}${'$'}serializer.prototype.${methodName}_"
    val startIndex = indexOf(start)
    val endIndex = indexOf("\n  };", startIndex)
    val method = substring(startIndex, endIndex)
    val methodBody = method.substringAfter("{\n")
    val newMethodBody = transform(methodBody)

    return replaceFirst(method, method.replaceFirst(methodBody, newMethodBody))
}

private enum class ValueType(
    encodeHash: String
) {
    STRING("61zpoe\$"),
    INT("za3lpa\$"),
    DOUBLE("14dthe\$"),

    ;

    val id: String by lazy {
        name.toLowerCase().capitalize()
    }

    val encodeMethodName: String by lazy {
        "encode${id}_$encodeHash"
    }

    val decodeMethodName: String by lazy {
        "decode${id}"
    }
}

private fun String.encodeMethodName(): String {
    val type = ValueType.values().first {
        "encode${it.id}Element" in this
    }

    return type.encodeMethodName
}

private fun String.decodeMethodName(): String {
    val type = ValueType.values().first {
        "decode${it.id}Element" in this
    }

    return type.decodeMethodName
}

