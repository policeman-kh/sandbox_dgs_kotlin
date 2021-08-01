package sandbox.dgs.data

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sandbox.dgs.gen.types.Author
import sandbox.dgs.gen.types.Category
import sandbox.dgs.gen.types.Sticker
import sandbox.dgs.model.InternalSticker

@Component
class ContentDataProvider : DataProvider {
    override fun stickers(): Flux<InternalSticker> {
        return Flux.fromIterable(stickersSaved())
    }

    override fun stickerById(id: String): Mono<InternalSticker> {
        return Flux.fromIterable(stickersSaved())
            .filter { sticker -> sticker.sticker.id.equals(id) }
            .take(1)
            .singleOrEmpty()
    }

    override fun author(id: String): Mono<Author> {
        return Flux.fromIterable(authors)
            .filter { author -> author.id.equals(id) }
            .take(1)
            .singleOrEmpty()
    }

    override fun category(id: String): Mono<Category> {
        return Flux.fromIterable(categories)
            .filter { category -> category.id.equals(id) }
            .take(1)
            .singleOrEmpty()
    }

    override fun addSticker(sticker: InternalSticker): Mono<InternalSticker> {
        stickersAdded.add(sticker)
        return Mono.just(sticker)
    }

    private fun stickersSaved(): List<InternalSticker> {
        return stickers + stickersAdded
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
        val stickersAdded = mutableListOf<InternalSticker>()
    }
}