package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.DetailedUserCollection
import org.thechance.service_identity.domain.entity.User

fun DetailedUserCollection.toEntity(): User {
    return User(
        id = id.toHexString(),
        fullName = fullName,
        username = username,
        password = password,
        isDeleted = isDeleted,
        email = details.first().email,
        walletId = details.first().walletId,
        addresses = details.first().addresses.map { it.toHexString() },
        permissions = details.first().permissions.map { it.toHexString() }
    )
}

fun List<DetailedUserCollection>.toEntity(): List<User> {
    return map { it.toEntity() }
}