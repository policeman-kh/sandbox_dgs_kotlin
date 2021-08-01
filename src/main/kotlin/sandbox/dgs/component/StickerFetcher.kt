package sandbox.dgs.component

import com.netflix.graphql.dgs.*
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sandbox.dgs.data.DataProvider
import sandbox.dgs.gen.types.Author
import sandbox.dgs.gen.types.Category
import sandbox.dgs.gen.types.Sticker
import sandbox.dgs.model.InternalSticker
import sandbox.dgs.processor.IStickerProcessor

@DgsComponent
class StickerFetcher(val dataProvider: DataProvider, val stickerProcessor: IStickerProcessor) {
    @DgsQuery
    fun stickers(): Flux<InternalSticker> {
        return dataProvider.stickers()
    }

    @DgsQuery
    fun stickerById(@InputArgument id: String): Mono<InternalSticker> {
        return dataProvider.stickerById(id);
    }

    @DgsData(parentType = "Sticker", field = "author")
    fun author(dfe: DgsDataFetchingEnvironment): Mono<Author> {
        val sticker = dfe.getSource<InternalSticker>()
        return dataProvider.author(sticker.authorId)
    }

    @DgsData(parentType = "Sticker", field = "categories")
    fun categories(dfe: DgsDataFetchingEnvironment): Flux<Category> {
        val sticker = dfe.getSource<InternalSticker>()
        return Flux.fromIterable(sticker.categoryIds)
            .flatMap { categoryId -> dataProvider.category(categoryId).flux() }
    }

    @DgsData(parentType = "Subscription", field = "subscribeBooks")
    fun subscribeSticker(): Publisher<Sticker> {
        return stickerProcessor.publish()
    }
}