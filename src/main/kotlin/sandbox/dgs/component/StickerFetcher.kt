package sandbox.dgs.component

import com.netflix.graphql.dgs.*
import org.reactivestreams.Publisher
import sandbox.dgs.data.DataProvider
import sandbox.dgs.gen.types.Author
import sandbox.dgs.gen.types.Category
import sandbox.dgs.gen.types.Sticker
import sandbox.dgs.model.InternalSticker
import sandbox.dgs.processor.IStickerProcessor

@DgsComponent
class StickerFetcher(val dataProvider: DataProvider, val stickerProcessor: IStickerProcessor) {
    @DgsQuery
    fun stickers(): List<InternalSticker> {
        return dataProvider.stickers()
    }

    @DgsQuery
    fun stickerById(@InputArgument id: String): InternalSticker? {
        return dataProvider.stickerById(id);
    }

    @DgsData(parentType = "Sticker", field = "author")
    fun author(dfe: DgsDataFetchingEnvironment): Author? {
        val sticker = dfe.getSource<InternalSticker>()
        return dataProvider.author(sticker.authorId)
    }

    @DgsData(parentType = "Sticker", field = "categories")
    fun categories(dfe: DgsDataFetchingEnvironment): List<Category?> {
        val sticker = dfe.getSource<InternalSticker>()
        return sticker.categoryIds.map { categoryId -> dataProvider.category(categoryId) }
    }

    @DgsData(parentType = "Subscription", field = "subscribeBooks")
    fun subscribeSticker(): Publisher<Sticker> {
        return stickerProcessor.publish()
    }
}