import web.dom.document
import web.html.HTML.div

fun createView(value: String) {
    val container = document.createElement(div)

    container.style.apply {
        width = "100%"
        height = "100%"
        backgroundColor = "blue"
    }

    container.textContent = value

    document.body.appendChild(container)
}
