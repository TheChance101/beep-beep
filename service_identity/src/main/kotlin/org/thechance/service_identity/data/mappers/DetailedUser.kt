package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.DetailedUserCollection
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.endpoints.model.DetailedUserDto

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
        permissions = details.first().permissions.map { it }
    )
}

fun List<DetailedUserCollection>.toEntity(): List<User> {
    return map { it.toEntity() }
}

fun DetailedUserDto.toEntity(): User {
    return User(
        id = id,
        fullName = fullName,
        username = username,
        password = password,
        email = email,
        walletId = walletId,
        addresses = addresses,
        permissions = permissions
    )
}