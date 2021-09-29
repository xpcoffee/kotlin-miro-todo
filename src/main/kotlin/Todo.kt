import kotlinx.coroutines.*

/**
 * Entry point for the app
 */
fun main() = runBlocking {
    val miroProperties = getMiroProperties()

    if (miroProperties != null) {
        val client = MiroClient(miroProperties)
        client.describeBoard()
    }
}
