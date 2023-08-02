package org.thechance.service_identity.domain.entity

import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import org.thechance.service_identity.api.model.WalletDto
import org.thechance.service_identity.data.collection.WalletCollection

data class Wallet(
    val id: String,
    val userId: String,
    val walletBalance: Double
) {
    fun toWalletDto(): WalletDto {
        return WalletDto(
            id = id,
            userId = userId,
            walletBalance = walletBalance
        )
    }

    fun toCollection(): WalletCollection {
        return WalletCollection(
            id = ObjectId(this.id),
            userId = ObjectId(this.userId).toId(),
            walletBalance = this.walletBalance,
        )
    }
}
