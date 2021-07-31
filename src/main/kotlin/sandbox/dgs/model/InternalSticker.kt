package sandbox.dgs.model

import sandbox.dgs.gen.types.Sticker

data class InternalSticker(val sticker: Sticker, val authorId: String, val categoryIds: List<String>) {
    fun getId(): String? {
        return sticker.id
    }

    fun getName(): String? {
        return sticker.name
    }

    fun getDescription(): String? {
        return sticker.description
    }
}