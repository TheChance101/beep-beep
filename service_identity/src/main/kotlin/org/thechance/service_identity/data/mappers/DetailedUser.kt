package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.DetailedUserCollection
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.entity.Wallet
import org.thechance.service_identity.endpoints.model.DetailedUserDto

fun DetailedUserCollection.toEntity(wallet: Wallet): User {
    return User(
        id = id.toHexString(),
        fullName = fullName,
        username = username,
        password = password,
        email = details.first().email,
        wallet = wallet,
        addresses = details.first().addresses.map { it.toHexString() },
        permissions = details.first().permissions.map { it }
    )
}

fun List<DetailedUserCollection>.toEntity(wallet: Wallet): List<User> {
    return map { it.toEntity(wallet) }
}

fun DetailedUserDto.toEntity(wallet: Wallet): User {
    return User(
        id = id,
        fullName = fullName,
        username = username,
        password = password,
        email = "",
        wallet = wallet,
        addresses = addresses,
        permissions = permissions
    )
}