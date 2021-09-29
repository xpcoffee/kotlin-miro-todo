import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.coroutineScope

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
        board;
    }

    /**
     * Describe frames in the Miro board
     */
    suspend fun describeFrames(): List<MiroFrame> = coroutineScope {
        val frames: DescribeMiroFramesResponse  = httpClient.get("https://api.miro.com/v1/boards/${properties.board}/widgets/?widgetType=frame") {
            headers {
                append(HttpHeaders.Accept, ContentType.Application.Json)
                append(HttpHeaders.Authorization, "Bearer ${properties.apiKey}")
            }
        }
        frames.data;
    }

    /**
     * Describe stickers in the Miro board
     */
    suspend fun describeStickers(): List<MiroSticker> = coroutineScope {
        val frames: DescribeMiroStickersResponse  = httpClient.get("https://api.miro.com/v1/boards/${properties.board}/widgets/?widgetType=sticker") {
            headers {
                append(HttpHeaders.Accept, ContentType.Application.Json)
                append(HttpHeaders.Authorization, "Bearer ${properties.apiKey}")
            }
        }
        frames.data;
    }
}