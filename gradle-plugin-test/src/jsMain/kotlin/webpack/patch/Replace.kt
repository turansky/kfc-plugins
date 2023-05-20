@file:Suppress(
    "NAME_CONTAINS_ILLEGAL_CHARS",
)

package webpack.patch

@JsName("import.meta.env.VITE_BUILD_NAME")
external val BUILD_NAME: String

@JsName("import.meta.env.VITE_BUILD_NUMBER")
external val BUILD_NUMBER: String

@JsName("import.meta.env.VITE_BUILD_NUMBER")
external val ANOTHER_BUILD_NUMBER: String
