import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import org.w3c.dom.url.URL
import org.w3c.fetch.RequestInit
import kotlin.js.Date
import kotlin.js.Promise

@JsExport
@OptIn(ExperimentalJsExport::class)
fun load(url: URL): Promise<String> =
    window.fetch("https://httpbin.org/get", RequestInit(headers = js("({})")))
        .then { response -> response.json() }
        .then { it.unsafeCast<String>() }

@JsExport
@OptIn(ExperimentalJsExport::class)
fun embed(element: HTMLElement) {
    document.body!!.appendChild(element)
}

@JsExport
@OptIn(ExperimentalJsExport::class)
fun now(): Date =
    Date()
