package org.thechance.service_identity.domain.entity

import org.bson.types.ObjectId
import org.thechance.service_identity.api.model.AddressDto
import org.thechance.service_identity.data.collection.AddressCollection

data class Address(
    val id: String,
    val userId: String,
    val latitude: Double,
    val longitude: Double,
    val isDeleted: Boolean? = null
) {
    fun toAddressDto(): AddressDto {
        return AddressDto(
            id = id,
            userId = userId,
            latitude = latitude,
            longitude = longitude
        )
    }

    fun toAddressCollection(): AddressCollection {
        return AddressCollection(
            userId = ObjectId(this.userId),
            latitude = this.latitude,
            longitude = this.longitude
        )
    }
}