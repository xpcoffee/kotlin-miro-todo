import kotlinx.coroutines.*

/**
 * Entry point for the app
 */
fun main() = runBlocking {
    val miroProperties = getMiroProperties()

    if (miroProperties != null) {
        val client = MiroClient(miroProperties)

        /**
         * Get the frames containing the TODO stickers
         */
        val frames = client.describeFrames()
        val todoFrames = frames.filter { frame -> miroProperties.frames.indexOf(frame.title) > -1 }

        /**
         * Get all stickers in the board
         */
        val stickers = client.describeStickers()
        val stickerMap = stickers.map { it.id to it.text }.toMap();

        /**
         * Print the contents of stickers in the TODO frames
         */
        printTodoState(todoFrames, stickerMap)
    }
}

/**
 * Prints the stickers in the TODO frames
 */
private fun printTodoState(
    todoFrames: List<MiroFrame>,
    stickerMap: Map<String, String>
) {
    todoFrames.forEach { frame ->
        println("---------------")
        println(frame.title)
        transformWidgetIdsToStickerContents(frame.children, stickerMap).forEach { sticker -> println(sticker) }
    }
}

/**
 * Returns the contents of stickers for a given list of widget ids and a map of stickers.
 */
fun transformWidgetIdsToStickerContents(widgetIds: List<String>, stickerMap: Map<String,String>): List<String> {
    return widgetIds.mapNotNull { id -> stickerMap.get(id) }
}
