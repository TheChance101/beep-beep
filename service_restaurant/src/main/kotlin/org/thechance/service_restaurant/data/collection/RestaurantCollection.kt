package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.thechance.service_restaurant.entity.Restaurant
import java.time.LocalTime


@Serializable
data class RestaurantCollection(
    val name: String,
    val description: String,
    val priceLevel: String,
    val rate: Double,
    val phone: String,

    val openingTime: String,
    val closingTime: String,
) {
    @BsonId
    @Contextual
    @SerialName("_id")
    val id = ObjectId()
    val isDeleted: Boolean = false

    fun toEntity(): Restaurant {
        return Restaurant(
            id = id.toString(),
            name = name,
            description = description,
            priceLevel = priceLevel,
            rate = rate,
            phone = phone,
            openingTime = openingTime,
            closingTime = closingTime
        )
    }
}

fun List<RestaurantCollection>.toEntity(): List<Restaurant> = map { it.toEntity() }

