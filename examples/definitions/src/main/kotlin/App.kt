import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import org.w3c.dom.url.URL
import org.w3c.fetch.RequestInit
import kotlin.js.Date
import kotlin.js.Promise

@JsExport
@ExperimentalJsExport
fun load(url: URL): Promise<String> =
    window.fetch("https://httpbin.org/get", RequestInit(headers = js("({})")))
        .then { response -> response.json() }
        .then { it.unsafeCast<String>() }

@JsExport
@ExperimentalJsExport
fun embed(element: HTMLElement) {
    document.body!!.appendChild(element)
}

@JsExport
@ExperimentalJsExport
fun now(): Date =
    Date()
