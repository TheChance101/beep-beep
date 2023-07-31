package org.thechance.service_identity.data.collection

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.thechance.service_identity.entity.Wallet

data class WalletCollection(
    @BsonId
    val id: ObjectId = ObjectId(),
    val userId: Id<UserCollection>,
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

