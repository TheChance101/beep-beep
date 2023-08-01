package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import org.thechance.service_identity.domain.entity.UserDetails
import org.thechance.service_identity.domain.entity.Wallet

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
)

fun UserDetailsCollection.toEntity(): UserDetails {
    return UserDetails(
        id = id.toHexString(),
        userId = userId,
        password = password,
        email = email,
        wallet = wallet?.toWallet(),
        addresses = addresses.map { it.toHexString() },
        permissions = permissions.map { it.toHexString() }
    )
}

fun List<UserDetailsCollection>.toEntity(): List<UserDetails> {
    return map { it.toEntity() }
}

fun UserDetails.toCollection(): UserDetailsCollection {
    return UserDetailsCollection(
        id = ObjectId(id),
        userId = userId,
        password = password,
        email = email,
        wallet = wallet?.toCollection(),
        addresses = addresses.map { ObjectId(it) },
        permissions = permissions.map { ObjectId(it) }
    )
}

fun Wallet.toCollection(): WalletCollection {
    return WalletCollection(
        id = ObjectId(id),
        userId = ObjectId(userId).toId(),
        walletBalance = walletBalance
    )
}

fun List<UserDetails>.toCollection(): List<UserDetailsCollection> {
    return map { it.toCollection() }
}