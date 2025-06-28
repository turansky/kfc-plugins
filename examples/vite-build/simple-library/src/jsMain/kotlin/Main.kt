import web.dom.document
import web.html.HtmlTagName.div
import web.html.HtmlTagName.span

fun createView() {
    val container = document.createElement(div)

    container.style.apply {
        width = "100%"
        height = "100%"
        backgroundColor = "blue"
    }

    container.textContent = "Simple App"

    val buildName = document.createElement(span)
    buildName.textContent = BUILD_NAME

    val buildNumber = document.createElement(span)
    buildNumber.textContent = BUILD_NUMBER

    container.appendChild(buildName)
    container.appendChild(buildNumber)

    document.body.appendChild(container)
}
