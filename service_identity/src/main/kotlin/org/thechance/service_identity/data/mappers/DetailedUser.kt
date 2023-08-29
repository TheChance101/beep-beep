package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.DetailedUserCollection
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.User

fun DetailedUserCollection.toEntity(
    walletBalance: Double,
    addresses: List<Address>,
    permission : Int
): User {
    return User(
        id = id.toString(),
        fullName = fullName,
        username = username,
        email = email,
        walletBalance = walletBalance,
        addresses = addresses,
        permission = permission
    )
}

fun List<DetailedUserCollection>.toEntity(
    walletBalance: Double,
    addresses: List<Address>,
    permission: Int
): List<User> {
    return map { it.toEntity(walletBalance, addresses, permission) }
}