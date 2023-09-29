package org.thechance.service_identity.domain.entity

data class Wallet(
    val id: String,
    val userId: String,
    val walletBalance: Double = 0.0,
    val currency: String
)
