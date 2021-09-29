import kotlinx.serialization.Serializable

/**
 * Board data from Miro API
 */
@Serializable
data class MiroBoard(val type: String, val name: String)