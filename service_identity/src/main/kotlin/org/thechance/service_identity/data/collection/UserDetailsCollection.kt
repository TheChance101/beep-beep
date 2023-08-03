package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.thechance.service_identity.domain.entity.UserDetails

@Serializable
data class UserDetailsCollection(
    @Contextual
    @SerialName("user_id")
    val userId: ObjectId,
    @SerialName("email")
    val email: String? = null,
    @SerialName("wallet")
    val walletId: String? = null,
    @SerialName("addresses")
    val addresses: List<@Contextual ObjectId> = emptyList(),
    @SerialName("permissions")
    val permissions: List<@Contextual ObjectId> = emptyList()
)

fun UserDetailsCollection.toEntity(): UserDetails {
    return UserDetails(
        userId = userId.toHexString(),
        email = email,
        walletId = walletId,
        addresses = addresses.map { it.toHexString() },
        permissions = permissions.map { it.toHexString() }
    )
}

fun List<UserDetailsCollection>.toEntity(): List<UserDetails> {
    return map { it.toEntity() }
}

fun UserDetails.toCollection(): UserDetailsCollection {
    return UserDetailsCollection(
        userId = ObjectId(userId),
        email = email,
        walletId = walletId,
        addresses = addresses.map { ObjectId(it) },
        permissions = permissions.map { ObjectId(it) }
    )
}

fun List<UserDetails>.toCollection(): List<UserDetailsCollection> {
    return map { it.toCollection() }
}