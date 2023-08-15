package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.DetailedUserCollection
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.entity.User

fun DetailedUserCollection.toEntity(
    walletBalance: Double,
    addresses: List<Address>,
    permissions: List<Permission>
): User {
    return User(
        id = id.toString(),
        fullName = fullName,
        username = username,
        email = email,
        walletBalance = walletBalance,
        addresses = addresses,
        permissions = permissions
    )
}

fun List<DetailedUserCollection>.toEntity(
    walletBalance: Double,
    addresses: List<Address>,
    permissions: List<Permission>
): List<User> {
    return map { it.toEntity(walletBalance, addresses, permissions) }
}