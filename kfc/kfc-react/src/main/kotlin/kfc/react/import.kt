package kfc.react

import react.ComponentModule
import react.ComponentType
import react.Props
import kotlin.js.Promise

@JsName("__react_component_import__")
external fun <P : Props> import(
    type: ComponentType<P>,
): Promise<ComponentModule<P>>
