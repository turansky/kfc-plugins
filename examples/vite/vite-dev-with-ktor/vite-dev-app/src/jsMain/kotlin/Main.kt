import web.http.fetchAsync
import web.prompts.alert

private suspend fun main() {
    fetchAsync("/api/data").await().textAsync().then {
        alert("Important data from server: $it")
    }

    createView()
}
