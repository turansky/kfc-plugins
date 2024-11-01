import web.dom.document
import web.html.HTML.div

private fun main() {
    val root = document.createElement(div)

    root.innerHTML = "Vite Dev App Started! Change me..."

    document.body.appendChild(root)
}
