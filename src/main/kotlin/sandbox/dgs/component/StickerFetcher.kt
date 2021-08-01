package sandbox.dgs.component

import com.netflix.graphql.dgs.*
import com.netflix.graphql.dgs.context.DgsContext
import graphql.schema.DataFetchingEnvironment
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
class StickerFetcher(
    val dataProvider: DataProvider, val stickerProcessor: IStickerProcessor
) {
    @DgsQuery
    fun stickers(dfe: DataFetchingEnvironment): Flux<InternalSticker> {
        val context = DgsContext.getCustomContext<AppContext>(dfe)
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

    @DgsMutation(field = "addSticker")
    fun registerSticker(dfe: DgsDataFetchingEnvironment): Mono<InternalSticker> {
        val id = dfe.getArgument<String>("id")
        val name = dfe.getArgument<String>("name")
        val description = dfe.getArgument<String>("description")
        val sticker = Sticker(id, name, description)
        return dataProvider.addSticker(InternalSticker(sticker, "1", listOf()))
            .doOnSuccess {
                stickerProcessor.emit(sticker)
            }
    }

    @DgsSubscription(field = "subscribeBooks")
    fun subscribeSticker(): Publisher<Sticker> {
        return stickerProcessor.publish()
    }
}