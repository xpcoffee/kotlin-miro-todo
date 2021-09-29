import kotlinx.serialization.Serializable;

@Serializable
data class MiroSticker(val id: String, val text: String)

@Serializable
data class DescribeMiroStickersResponse(val data: List<MiroSticker>)
