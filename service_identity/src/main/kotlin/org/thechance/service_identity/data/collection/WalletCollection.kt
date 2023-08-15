package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import java.util.UUID

@Serializable
data class WalletCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: UUID = UUID.randomUUID(),
    @SerialName("user_id")
    val userId: String,
    @SerialName("wallet_balance")
    val walletBalance: Double = 0.0,
    @SerialName("is_deleted")
    val isDeleted: Boolean = false
)

