package org.thechance.service_identity.data.collection.mappers

import org.thechance.service_identity.data.collection.DetailedUserCollection
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.User

fun DetailedUserCollection.toEntity(
    walletBalance: Double, addresses: List<Address>, country: String, permission: Int
): User {
    return User(
        id = id.toString(),
        fullName = fullName,
        username = username,
        email = email,
        walletBalance = walletBalance,
        addresses = addresses,
        country = country,
        permission = permission
    )
}

fun List<DetailedUserCollection>.toEntity(
    walletBalance: Double, addresses: List<Address>, country: String, permission: Int
): List<User> {
    return map { it.toEntity(walletBalance, addresses, country, permission) }
}