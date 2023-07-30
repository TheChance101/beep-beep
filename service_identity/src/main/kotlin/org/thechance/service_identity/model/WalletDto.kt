package org.thechance.service_identity.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.thechance.service_identity.entity.Wallet

@Serializable
data class WalletDto(
    @BsonId
    val id: Id<WalletDto>? = null,
    val userId: Id<UserDto>,
    val walletBalance: Double
)

fun WalletDto.toWallet(): Wallet {
    return Wallet(
        id = id.toString(),
        userId = userId.toString(),
        walletBalance = walletBalance
    )
}
