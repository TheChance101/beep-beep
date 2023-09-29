package org.thechance.service_identity.data.collection.mappers

import org.thechance.service_identity.data.collection.WalletCollection
import org.thechance.service_identity.domain.entity.Wallet


fun WalletCollection.toEntity() = Wallet(
    id = id.toString(),
    userId = userId.toString(),
    walletBalance = walletBalance,
    currency = currency
)

