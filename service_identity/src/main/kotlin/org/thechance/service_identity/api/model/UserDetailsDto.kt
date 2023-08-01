package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.domain.entity.UserDetails

@Serializable
data class UserDetailsDto(
    val id: String,
    val userId: String,
    val password: String,
    val email: String,
    val wallet: WalletDto,
    val addresses: List<String>,
    val permissions: List<String>
){
    fun toUserDetails():UserDetails{
        return UserDetails(
            id = id,
            userId = userId,
            password = password,
            email = email,
            wallet = wallet.toWallet(),
            addresses = addresses,
            permissions = permissions
        )
    }
}
