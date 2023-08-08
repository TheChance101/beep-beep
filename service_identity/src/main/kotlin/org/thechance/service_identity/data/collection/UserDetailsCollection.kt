package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class UserDetailsCollection(
    @Contextual
    @SerialName("user_id")
    val userId: ObjectId,
    @SerialName("email")
    val email: String,
    @SerialName("wallet")
    val walletCollection: WalletCollection,
    @SerialName("addresses")
    val addresses: List<@Contextual ObjectId> = emptyList(),
    @SerialName("permissions")
    val permissions: List<Int> = emptyList()
)