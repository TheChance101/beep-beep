package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserDetailsCollection(
    @Contextual
    @SerialName("user_id")
    val userId: UUID? = null,
    @SerialName("wallet")
    val walletCollection: WalletCollection? = null,
    @SerialName("addresses")
    val addresses: List<@Contextual UUID> = emptyList()
)