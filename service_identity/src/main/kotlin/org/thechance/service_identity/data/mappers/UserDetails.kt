package org.thechance.service_identity.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_identity.data.collection.UserDetailsCollection
import org.thechance.service_identity.domain.entity.UserDetails

fun UserDetailsCollection.toEntity(): UserDetails {
    return UserDetails(
        userId = userId.toHexString(),
        email = email,
        walletId = walletId,
        addresses = addresses.map { it.toHexString() },
        permissions = permissions.map { it }
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
        permissions = permissions.map { it }
    )
}

fun List<UserDetails>.toCollection(): List<UserDetailsCollection> {
    return map { it.toCollection() }
}