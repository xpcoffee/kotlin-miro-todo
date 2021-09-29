import io.ktor.client.*;
import io.ktor.client.engine.apache.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.*
import java.io.FileInputStream
import java.util.*


/**
 * Entry point for the app
 */
fun main() = runBlocking {
    val miroProperties = getMiroProperties();
    println(miroProperties);

    if(miroProperties != null) {
        callMiro(miroProperties)
    }
}

/**
 * Read and return Miro configuration
 */
fun getMiroProperties(): MiroProperties? {
    val miroPropertiesFile = "miro.properties"
    val propertiesPath = object {}.javaClass.getResource(miroPropertiesFile)?.path;
    if(propertiesPath == null) {
        println("Unable to find: $miroPropertiesFile")
        return null;
    }

    val properties = Properties()
    properties.load(FileInputStream(propertiesPath))

    return MiroProperties(
        board = properties.getProperty("board"),
        apiKey = properties.getProperty("apiKey")
    );
}

/**
 * Make a call against Miro
 */
suspend fun callMiro(properties: MiroProperties): Unit = coroutineScope {
    val httpClient: HttpClient = HttpClient(Apache) {
        ResponseObserver { response -> println("HTTP status : ${response.status.value}") }
    }
    val response = async { httpClient.get<HttpResponse>("https://google.com") {} }
}

/**
 * Dataclass that models Miro configuration
 */
data class MiroProperties(val board: String, val apiKey: String);