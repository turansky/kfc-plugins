package kfc.import

fun <T : Any> JsClass<T>.newInstance(): T =
    newInstance(this)

@Suppress(
    "UNUSED_PARAMETER",
)
private fun <T : Any> newInstance(
    clazz: JsClass<T>,
): T = js("new clazz()")
