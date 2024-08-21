import web.dom.document
import web.html.HTML.div
import web.html.HTMLElement

private fun main() {
    document.body.appendChild(View())
}

internal fun View(): HTMLElement {
    val container = document.createElement(div)

    container.style.apply {
        width = "100%"
        height = "100%"
        backgroundColor = "blue"
    }

    container.textContent = "Simple Vite Application"

    return container
}
