package org.thechance.api_gateway.data.model.identity

/**
 * Created by Aziza Helmy on 8/14/2023.
 */
data class User(
    val id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val walletBalance: Double = 0.0,
    val addresses: List<AddressResource> = emptyList(),
    val permissions: List<PermissionResource> = emptyList()
)



