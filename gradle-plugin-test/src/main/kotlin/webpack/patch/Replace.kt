package webpack.patch

@JsName("__BUILD_NAME__")
external val BUILD_NAME: String

@JsName("__BUILD_NUMBER__")
external val BUILD_NUMBER: Int

val ANOTHER_BUILD_NUMBER: Int = "__BUILD_NUMBER__".toInt()