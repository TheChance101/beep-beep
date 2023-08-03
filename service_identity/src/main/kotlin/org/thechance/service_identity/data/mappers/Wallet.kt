package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.WalletCollection
import org.thechance.service_identity.domain.entity.Wallet

fun WalletCollection.toWallet(): Wallet {
    return Wallet(
        id = id.toHexString(),
        userId = userId,
        walletBalance = walletBalance
    )
}