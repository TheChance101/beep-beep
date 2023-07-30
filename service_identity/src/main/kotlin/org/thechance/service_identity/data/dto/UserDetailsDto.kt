package org.thechance.service_identity.data.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class UserDetailsDto(
    @BsonId
    val id: Id<UserDetailsDto>? = null,
    val userId: Id<UserDto>,
    val password: String,
    val email: String,
    @Contextual
    val address: AddressDto,
)
