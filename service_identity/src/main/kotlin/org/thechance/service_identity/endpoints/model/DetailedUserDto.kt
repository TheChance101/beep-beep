package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.api.model.WalletDto

@Serializable
data class DetailedUserDto(
    val id: String,
    val fullName: String,
    val username: String,
    val password: String,
    val wallet: WalletDto,
    val addresses: List<String> = emptyList(),
    val permissions: List<Int> = emptyList()
)