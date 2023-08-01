package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.thechance.service_identity.domain.entity.UserDetails

@Serializable
data class UserDetailsCollection(
    @SerialName("_id")
    @Contextual
    val id: ObjectId = ObjectId(),
    @SerialName("user_id")
    val userId: String,
    @SerialName("password")
    val password: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("wallet")
    val wallet: WalletCollection? = null,
    @SerialName("addresses")
    val addresses: List<@Contextual ObjectId> = emptyList(),
    @SerialName("permissions")
    val permissions: List<@Contextual ObjectId> = emptyList()
) {
    fun toUserDetails(): UserDetails {
        return UserDetails(
            id = id.toHexString(),
            userId = userId.toString(),
            password = password,
            email = email,
            wallet = wallet.toWallet(),
            addresses = addresses.map { it.toString() },
            permissions = permissions.map { it.toString() }
        )
    }
}


