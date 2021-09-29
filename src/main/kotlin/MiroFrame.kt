import kotlinx.serialization.Serializable

@Serializable
data class MiroFrame(val title: String, val children: List<String>)

@Serializable
data class DescribeMiroFramesResponse(val data: List<MiroFrame>)
