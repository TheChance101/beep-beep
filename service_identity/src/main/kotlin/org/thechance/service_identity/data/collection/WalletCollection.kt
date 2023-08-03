package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.thechance.service_identity.domain.entity.Wallet

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
) {
    fun toEntity(): Wallet {
        return Wallet(
            id = id.toHexString(),
            userId = userId,
            walletBalance = walletBalance,
            isDeleted = isDeleted
        )
    }
}

