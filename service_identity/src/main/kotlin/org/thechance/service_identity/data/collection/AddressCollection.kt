package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.thechance.service_identity.entity.Address

@Serializable
data class AddressCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    @SerialName("user_id")
    val userId: Id<UserCollection>,
    @SerialName("country")
    val country: String,
    @SerialName("city")
    val city: String,
    @SerialName("street")
    val street: String? = null,
    @SerialName("zip_code")
    val zipCode: Long? = null,
    @SerialName("house_number")
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


