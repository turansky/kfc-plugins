import web.dom.document
import web.html.HtmlSource
import web.html.HtmlTagName.div

private fun main() {
    val root = document.createElement(div)

    root.innerHTML = HtmlSource("Vite Dev App Started! Change me...")

    document.body.appendChild(root)
}
