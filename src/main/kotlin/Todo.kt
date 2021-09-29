import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.serialization.*
import java.io.FileInputStream
import java.util.*


/**
 * Entry point for the app
 */
fun main() = runBlocking {
    val miroProperties = getMiroProperties()
    println(miroProperties)

    if (miroProperties != null) {
        callMiro(miroProperties)
    }
}

/**
 * Read and return Miro configuration
 */
fun getMiroProperties(): MiroProperties? {
    val miroPropertiesFile = "miro.properties"
    val propertiesPath = object {}.javaClass.getResource(miroPropertiesFile)?.path
    if (propertiesPath == null) {
        println("Unable to find: $miroPropertiesFile")
        return null
    }

    val properties = Properties()
    properties.load(FileInputStream(propertiesPath))

    return MiroProperties(
        board = properties.getProperty("board"),
        apiKey = properties.getProperty("accessToken")
    )
}

/**
 * Make a call against Miro
 */
suspend fun callMiro(properties: MiroProperties): Unit = coroutineScope {
    val httpClient = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        ResponseObserver { response -> println("HTTP status : ${response.status.value}") }
    }
    val board: MiroBoard = httpClient.get("https://api.miro.com/v1/boards/${properties.board}") {
        headers {
            append(HttpHeaders.Accept, ContentType.Application.Json)
            append(HttpHeaders.Authorization, "Bearer ${properties.apiKey}")
        }
    }
    println(board)
}

/**
 * Dataclass that models Miro configuration
 */
data class MiroProperties(val board: String, val apiKey: String)

/**
 * Board data from Miro API
 */
@Serializable
data class MiroBoard(val type: String, val name: String)