package org.w3c.dom

import org.w3c.dom.css.CSSStyleDeclaration

@JsName("HTMLElement")
open external class CustomElement : HTMLElement {
    protected open fun connectedCallback()
    protected open fun disconnectedCallback()
    protected open fun adoptedCallback()
    protected open fun attributeChangedCallback(
        name: Boolean,
        oldValue: dynamic,
        newValue: dynamic,
    )

    override val childElementCount: Int
    override val children: HTMLCollection
    override var contentEditable: String
    override val isContentEditable: Boolean
    override val style: CSSStyleDeclaration

    override fun after(vararg nodes: dynamic)
    override fun append(vararg nodes: dynamic)
    override fun before(vararg nodes: dynamic)
    override fun convertPointFromNode(
        point: DOMPointInit,
        from: dynamic,
        options: ConvertCoordinateOptions,
    ): DOMPoint

    override fun convertQuadFromNode(
        quad: dynamic,
        from: dynamic,
        options: ConvertCoordinateOptions,
    ): DOMQuad

    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: dynamic,
        options: ConvertCoordinateOptions,
    ): DOMQuad

    override fun getBoxQuads(options: BoxQuadOptions): Array<DOMQuad>
    override fun prepend(vararg nodes: dynamic)
    override fun querySelector(selectors: String): Element?
    override fun querySelectorAll(selectors: String): NodeList
    override fun remove()
    override fun replaceWith(vararg nodes: dynamic)
}
