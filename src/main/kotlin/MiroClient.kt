import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json

/**
 * Client which returns data from the Miro API
 */
class MiroClient(val properties: MiroProperties) {
    val httpClient: HttpClient = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        ResponseObserver { response -> println("HTTP status : ${response.status.value}") }
    }

    /**
     * Describe the Miro board
     */
    suspend fun describeBoard() = coroutineScope {
        val board: MiroBoard = httpClient.get("https://api.miro.com/v1/boards/${properties.board}") {
            headers {
                append(HttpHeaders.Accept, ContentType.Application.Json)
                append(HttpHeaders.Authorization, "Bearer ${properties.apiKey}")
            }
        }
        println(board)
    }
}