package sandbox.dgs.data

import sandbox.dgs.gen.types.Author
import sandbox.dgs.gen.types.Category
import sandbox.dgs.model.InternalSticker

interface DataProvider {
    fun stickers(): List<InternalSticker>
    fun stickerById(id: String): InternalSticker?
    fun author(id: String): Author?
    fun category(id: String): Category?
}