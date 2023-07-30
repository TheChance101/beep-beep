package org.thechance.service_identity.data.dto

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class AddressDto(
    @BsonId
    val id: Id<AddressDto>? = null,
    val userId: Id<UserDto>,
    val country: String,
    val city: String,
    val street: String? = null,
    val code: String? = null,
    val houseNumber: Int? = null
)
