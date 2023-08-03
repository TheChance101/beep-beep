package org.thechance.service_identity.domain.entity

import org.bson.types.ObjectId
import org.thechance.service_identity.api.model.WalletDto
import org.thechance.service_identity.data.collection.WalletCollection

data class Wallet(
    val id: String,
    val userId: String,
    val walletBalance: Double,
    val isDeleted: Boolean = false
) {
    fun toDto(): WalletDto {
        return WalletDto(
            id = id,
            userId = userId,
            walletBalance = walletBalance
        )
    }

    fun toCollection(): WalletCollection {
        return WalletCollection(
            id = ObjectId(this.id),
            userId = userId,
            walletBalance = this.walletBalance,
            isDeleted = this.isDeleted
        )
    }
}
