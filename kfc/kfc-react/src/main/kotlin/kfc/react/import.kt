package kfc.react

import react.ComponentModule
import react.ComponentType
import react.Props

@JsName("__react_component_import__")
external fun <P : Props> import(
    type: ComponentType<P>,
): ComponentModule<P>
