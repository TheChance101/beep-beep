package org.thechance.service_identity.entity

import org.thechance.service_identity.api.model.WalletDto

data class Wallet(
    val id: String,
    val userId: String,
    val walletBalance: Double
){
    fun toWalletDto(): WalletDto {
        return WalletDto(
            id = id,
            userId = userId,
            walletBalance = walletBalance
        )
    }
}
