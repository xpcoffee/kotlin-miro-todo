import kotlinx.coroutines.*

/**
 * Entry point for the app
 */
fun main() = runBlocking {
    val miroProperties = getMiroProperties()

    if (miroProperties != null) {
        val client = MiroClient(miroProperties)
        val frames = client.describeFrames()
        val todoFrames = frames.filter { frame -> miroProperties.frames.indexOf(frame.title) > -1 }

        val stickers = client.describeStickers()
        val stickerMap = stickers.map { it.id to it.text }.toMap();


        todoFrames.forEach { frame ->
            println("---------------")
            println(frame.title)
            transformStickerIdsToStickerContents(frame.children, stickerMap).forEach { sticker -> println(sticker) }
        }
    }
}

fun transformStickerIdsToStickerContents(ids: List<String>, stickerMap: Map<String,String>): List<String> {
    return ids.mapNotNull { id -> stickerMap.get(id) }
}
