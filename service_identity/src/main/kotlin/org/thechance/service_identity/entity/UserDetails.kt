package org.thechance.service_identity.entity

import org.thechance.service_identity.api.model.UserDetailsDto

data class UserDetails(
    val id: String,
    val userId: String,
    val password: String,
    val email: String,
    val wallet: Wallet,
    val addresses: List<String>,
    val permissions: List<String>
){
    fun toUserDetailsDto(): UserDetailsDto {
        return UserDetailsDto(
            id = id,
            userId = userId,
            password = password,
            email = email,
            wallet = wallet.toWalletDto(),
            addresses = addresses,
            permissions = permissions
        )
    }
}
