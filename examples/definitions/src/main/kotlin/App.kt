import kotlinx.browser.window
import org.w3c.dom.url.URL
import org.w3c.fetch.RequestInit
import kotlin.js.Promise

@JsExport
@OptIn(ExperimentalJsExport::class)
fun load(url: URL): Promise<String> =
    window.fetch("https://httpbin.org/get", RequestInit(headers = js("({})")))
        .then { response -> response.json() }
        .then { it.unsafeCast<String>() }
