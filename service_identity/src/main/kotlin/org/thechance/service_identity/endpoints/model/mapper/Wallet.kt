package org.thechance.service_identity.endpoints.model.mapper

import org.thechance.service_identity.domain.entity.Wallet
import org.thechance.service_identity.endpoints.model.WalletDto


fun Wallet.toDto() = WalletDto(id = id, userId = userId, walletBalance = walletBalance,currency=currency)