import io.ktor.client.*;
import io.ktor.client.engine.apache.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

suspend fun main(args: Array<String>) {
    val httpClient: HttpClient = HttpClient(Apache) {
        ResponseObserver { response -> println("HTTP status : ${response.status.value}") }
    }
    val response = httpClient.get<HttpResponse>("https://google.com") {};
}