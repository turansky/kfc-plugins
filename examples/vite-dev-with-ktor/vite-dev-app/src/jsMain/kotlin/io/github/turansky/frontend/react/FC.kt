package react

import js.objects.unsafeJso
import js.reflect.unsafeCast

inline fun FC(
    crossinline block: @ReactDsl ChildrenBuilder.() -> Unit,
): FC<Props> =
    unsafeCast(
        provider = { ->
            createElementOrNull(block)
        },
    )

inline fun <P : Props> FC(
    crossinline block: @ReactDsl ChildrenBuilder.(props: P) -> Unit,
): FC<P> =
    unsafeCast(
        provider = { props: P ->
            createElementOrNull { block(props) }
        },
    )

@OptIn(js.internal.InternalApi::class)
inline fun createElementOrNull(
    crossinline block: @ReactDsl ChildrenBuilder.() -> Unit,
): ReactElement<*> {
    return Fragment.create {
        children = unsafeJso(block).buildChildren()
    }
}
