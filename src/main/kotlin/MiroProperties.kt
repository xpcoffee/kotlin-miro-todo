import java.io.FileInputStream
import java.util.*

/**
 * Dataclass that models Miro configuration
 */
data class MiroProperties(val board: String, val apiKey: String, val frames: List<String>)

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
        apiKey = properties.getProperty("accessToken"),
        frames = properties.getProperty("frames").split(",")
    )
}
