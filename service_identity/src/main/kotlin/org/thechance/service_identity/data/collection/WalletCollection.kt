package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.thechance.service_identity.entity.Wallet

@Serializable
data class WalletCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    @SerialName("user_id")
    val userId: Id<UserCollection>,
    @SerialName("wallet_balance")
    val walletBalance: Double
) {
    fun toWallet(): Wallet {
        return Wallet(
            id = id.toHexString(),
            userId = userId.toString(),
            walletBalance = walletBalance
        )
    }
}

