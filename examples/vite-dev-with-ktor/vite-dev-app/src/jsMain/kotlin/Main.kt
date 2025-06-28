import react.create
import react.dom.client.createRoot
import web.dom.document
import web.html.HtmlTagName.div

private fun main() {
    val container = document.createElement(div)
    container.style.apply {
        width = "100%"
        height = "100%"
        backgroundColor = "lightgray"
    }

    document.body.appendChild(container)

    createRoot(container)
        .render(Application.create())
}
