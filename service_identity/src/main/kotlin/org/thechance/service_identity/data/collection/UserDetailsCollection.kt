package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.thechance.service_identity.entity.UserDetails

data class UserDetailsCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    @SerialName("user_id")
    val userId: Id<UserCollection>,
    @SerialName("password")
    val password: String,
    @SerialName("email")
    val email: String,
    @SerialName("wallet")
    val wallet: WalletCollection,
    @SerialName("addresses")
    val addresses: List<ObjectId>,
    @SerialName("permissions")
    val permissions: List<ObjectId>
) {
    fun toUserDetails(): UserDetails {
        return UserDetails(
            id = id.toHexString(),
            userId = userId.toString(),
            password = password,
            email = email,
            wallet = wallet.toWallet(),
            addresses = addresses.map { it.toHexString() },
            permissions = permissions.map { it.toHexString() }
        )
    }
}


