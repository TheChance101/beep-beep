package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import java.util.*

@Serializable
data class UserDetailsCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: UUID = UUID.randomUUID(),
    @SerialName("user_Id")
    @Contextual
    val userId: UUID? = null,
    val walletCollection: WalletCollection? = null,
    val addresses: List<@Contextual String> = emptyList()
)