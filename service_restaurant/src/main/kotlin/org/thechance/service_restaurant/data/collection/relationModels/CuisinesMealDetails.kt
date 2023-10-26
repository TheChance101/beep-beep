package org.thechance.service_restaurant.data.collection.relationModels

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.thechance.service_restaurant.data.collection.MealCollection


@Serializable
data class CuisinesMealDetails(
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: ObjectId = ObjectId(),
    val name: String,
    val image: String,
    val meals: List<MealCollection> = emptyList()
)