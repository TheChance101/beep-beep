package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.domain.entity.Wallet

@Serializable
data class WalletDto(
    val id: String,
    val userId: String,
    val walletBalance: Double
) {
    fun toWallet(): Wallet {
        return Wallet(
            id = id,
            userId = userId,
            walletBalance = walletBalance
        )
    }
}