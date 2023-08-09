package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class UserDetailsCollection(
    @Contextual
    @SerialName("user_id")
    val userId: ObjectId? = null,
    @SerialName("wallet")
    val walletCollection: WalletCollection? = null,
    @SerialName("addresses")
    val addresses: List<@Contextual ObjectId> = emptyList()
)