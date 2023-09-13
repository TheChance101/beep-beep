package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class UserDetailsCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    @SerialName("user_Id")
    @Contextual
    val userId: ObjectId,
    val walletCollection: WalletCollection? = null,
    val country: String? = null,
    val addressIds: MutableList<@Contextual ObjectId> = mutableListOf(),
    val favorite: MutableList<@Contextual ObjectId> = mutableListOf(),
)