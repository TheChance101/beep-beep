package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable

@Serializable
data class WalletDto(
    val id: String,
    val userId: String,
    val walletBalance: Double
)