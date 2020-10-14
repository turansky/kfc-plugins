package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.WebComponent.Property.Type.*
import java.io.Serializable

private const val SOURCE = "this._source"
private const val ELEMENT = "$SOURCE.__element__"

// language=JavaScript
private fun redispatchEvent(type: String): String = """
    $ELEMENT.addEventListener('$type', e => {
      this.dispatchEvent(new CustomEvent('$type', { detail: e.detail }))
    })
""".trimIndent()

data class WebComponent(
    private val id: String,
    private val shadowMode: String,
    private val properties: List<Property>,
    private val methods: List<Method>,
    private val events: List<String>,
    private val source: String
) : Serializable {
    // language=JavaScript
    fun toCode(sourceModuleName: String): String = """
        import * as sourceModule from '$sourceModuleName'
        
        const SourceElement = sourceModule.$source
        
        export class $name extends HTMLElement {
           constructor() {
             super();
             
             $SOURCE = new SourceElement()      
             const shadow = this.attachShadow({ mode: '$shadowMode' })
             shadow.appendChild($ELEMENT)
             
             ${events.joinToString("\n", transform = ::redispatchEvent)}
           }
           
           ${properties.joinToString("\n\n")}
        
           ${methods.joinToString("\n\n")}
        }    
        
        customElements.define('$id', $name)
    """.trimIndent()

    private val name: String = source.substringAfterLast(".")

    data class Property(
        private val name: String,
        private val type: Type
    ) : Serializable {
        private fun toGetter(): String = """
            get $name() {
                return $SOURCE.$name
            }
        """.trimIndent()

        private fun toSetter(): String = """
            set $name(value) {
                $SOURCE.$name = value
            }
        """.trimIndent()

        override fun toString(): String =
            when (type) {
                RW -> toGetter() + "\n\n" + toSetter()
                RO -> toGetter()
                WO -> toSetter()
            }

        enum class Type {
            RW, RO, WO
        }
    }

    data class Method(
        private val name: String,
        private val parameterNames: List<String>
    ) : Serializable {
        override fun toString(): String {
            val method = "$name(${parameterNames.joinToString(", ")})"
            return """
                $method {
                    return $SOURCE.$method
                }
            """.trimIndent()
        }
    }
}
