package org.thechance.service_identity.domain.gateway

import org.thechance.service_identity.domain.entity.Wallet

interface WalletGateWay {
    suspend fun getWalletById(id: String): Wallet

    suspend fun getWalletByUserId(userId: String): Wallet

    suspend fun createWallet(wallet: Wallet): Boolean

    suspend fun updateWallet(id: String,wallet: Wallet): Boolean

    suspend fun deleteWallet(id: String): Boolean


}