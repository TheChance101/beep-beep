package org.thechance.service_identity.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_identity.api.model.WalletDto
import org.thechance.service_identity.data.collection.WalletCollection
import org.thechance.service_identity.domain.entity.Wallet

fun WalletCollection.toEntity(): Wallet {
    return Wallet(
        id = id.toHexString(),
        userId = userId,
        walletBalance = walletBalance
    )
}

fun Wallet.toWalletDto(): WalletDto {
    return WalletDto(
        id = id,
        userId = userId,
        walletBalance = walletBalance
    )
}

fun Wallet.toCollection(): WalletCollection {
    return WalletCollection(
        id = ObjectId(this.id),
        userId = userId,
        walletBalance = this.walletBalance,
    )
}