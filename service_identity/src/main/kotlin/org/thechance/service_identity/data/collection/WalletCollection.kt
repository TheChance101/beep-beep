package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.util.UUID

@Serializable
data class WalletCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    @Contextual
    val userId: ObjectId,
    val walletBalance: Double = 0.0,
    val currency: String = "",
    val isDeleted: Boolean = false
)

