package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.thechance.service_restaurant.entity.Category

@Serializable
@SerialName("categoryCollection")
data class CategoryCollection(
    val name: String? = null,
    val restaurantIds: MutableList<@Contextual ObjectId> = mutableListOf()
) {
    @BsonId
    @Contextual
    @SerialName("_id")
    val id = ObjectId()
    val isDeleted: Boolean = false

    fun toEntity(): Category {
        return Category(
            id = id.toString(),
            name = name,
            isDeleted = isDeleted,
        )
    }
}

fun List<CategoryCollection>.toEntity(): List<Category> = map { it.toEntity() }