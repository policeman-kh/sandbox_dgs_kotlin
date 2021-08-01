package sandbox.dgs.data

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sandbox.dgs.gen.types.Author
import sandbox.dgs.gen.types.Category
import sandbox.dgs.model.InternalSticker

interface DataProvider {
    fun stickers(): Flux<InternalSticker>
    fun stickerById(id: String): Mono<InternalSticker>
    fun author(id: String): Mono<Author>
    fun category(id: String): Mono<Category>
    fun addSticker(sticker: InternalSticker): Mono<InternalSticker>
}