package sandbox.dgs.data

import org.springframework.stereotype.Component
import sandbox.dgs.gen.types.Author
import sandbox.dgs.gen.types.Category
import sandbox.dgs.gen.types.Sticker
import sandbox.dgs.model.InternalSticker

@Component
class ContentDataProvider : DataProvider {
    override fun stickers(): List<InternalSticker> {
        return stickers
    }

    override fun stickerById(id: String): InternalSticker? {
        return stickers.filter { sticker -> sticker.sticker.id.equals(id) }.firstOrNull()
    }

    override fun author(id: String): Author? {
        return authors.filter { author -> author.id.equals(id) }.firstOrNull()
    }

    override fun category(id: String): Category? {
        return categories.filter { category -> category.id.equals(id) }.firstOrNull()
    }

    companion object {
        val authors = listOf(Author("1", "author1"), Author("2", "author2"))
        val categories = listOf(Category("1", "category1"), Category("2", "category2"))
        val stickers = listOf(
            InternalSticker(Sticker("1", "name1", "description1"), "1", listOf("1", "2")),
            InternalSticker(Sticker("2", "name2", "description2"), "1", listOf("1")),
            InternalSticker(Sticker("3", "name3", "description3"), "1", listOf()),
            InternalSticker(Sticker("4", "name4", "description4"), "2", listOf("1")),
            InternalSticker(Sticker("5", "name5", "description5"), "2", listOf()),
        )
    }
}