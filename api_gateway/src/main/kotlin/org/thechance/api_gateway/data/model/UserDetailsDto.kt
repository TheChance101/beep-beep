package org.thechance.api_gateway.data.model

import okhttp3.Address

data class UserDetailsDto(
    val id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val walletBalance: Double,
    val currency: String,
    val addresses: List<Address> = emptyList(),
    val country: String,
    val permission: Int = 1
    )
