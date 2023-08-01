package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.thechance.service_restaurant.entity.Address

@Serializable
data class AddressCollection(
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: ObjectId = ObjectId(),
    val lat: Long? = null,
    val lon: Long? = null,
    val isDeleted: Boolean = false,
) {
    fun toEntity(): Address {
        return Address(
            id = id.toString(),
            lat = lat,
            lon = lon,
            isDeleted = isDeleted
        )
    }
}

fun List<AddressCollection>.toEntity(): List<Address> = map { it.toEntity() }