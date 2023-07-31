package org.thechance.service_identity.data.collection

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.thechance.service_identity.entity.Address

data class AddressCollection(
    @BsonId
    val id: ObjectId = ObjectId(),
    val userId: Id<UserCollection>,
    val country: String,
    val city: String,
    val street: String? = null,
    val zipCode: Long? = null,
    val houseNumber: String? = null
){
    fun toAddress(): Address {
        return Address(
            id = id.toHexString(),
            userId = userId.toString(),
            country = country,
            city = city,
            street = street,
            zipCode = zipCode,
            houseNumber = houseNumber
        )
    }
}


