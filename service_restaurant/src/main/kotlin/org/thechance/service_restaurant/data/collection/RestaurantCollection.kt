package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.thechance.service_restaurant.entity.Address
import org.thechance.service_restaurant.entity.Restaurant


@Serializable
data class RestaurantCollection(
    val name: String? = null,
    val description: String? = null,
    val priceLevel: String? = null,
    val rate: Double? = null,
    val phone: String? = null,
    val openingTime: String? = null,
    val closingTime: String? = null,
) {
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: ObjectId = ObjectId()
    val isDeleted: Boolean = false
    val addressIds: List<@Contextual Id<AddressCollection>> = emptyList()

    fun toEntity(addresses: List<Address>): Restaurant {
        return Restaurant(
            id = id.toString(),
            name = name,
            description = description,
            priceLevel = priceLevel,
            rate = rate,
            phone = phone,
            openingTime = openingTime,
            closingTime = closingTime,
            isDeleted = isDeleted,
            addresses = addresses
        )
    }
}

