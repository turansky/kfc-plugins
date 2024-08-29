import web.dom.document
import web.html.HTML.div

fun createView() {
    val container = document.createElement(div)

    container.style.apply {
        width = "100%"
        height = "100%"
        backgroundColor = "blue"
    }

    container.textContent = "Simple App"

    document.body.appendChild(container)
}
