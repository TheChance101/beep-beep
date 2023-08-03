package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class WalletCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    @SerialName("user_id")
    val userId: String,
    @SerialName("wallet_balance")
    val walletBalance: Double,
    @SerialName("is_deleted")
    val isDeleted: Boolean = false
)

