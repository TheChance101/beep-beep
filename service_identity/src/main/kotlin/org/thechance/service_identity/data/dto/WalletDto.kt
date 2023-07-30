package org.thechance.service_identity.data.dto

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class WalletDto(
    @BsonId
    val id: Id<WalletDto>? = null,
    val userId: Id<UserDto>,
    val walletBalance: Double
)
