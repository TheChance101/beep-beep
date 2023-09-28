package org.thechance.service_identity.data.collection.mappers

import org.thechance.service_identity.data.collection.DetailedUser
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.User

fun DetailedUser.toEntity(
    walletBalance: Double, currency: String, addresses: List<Address>, country: String, permission: Int
) = User(
    id = id.toString(),
    fullName = fullName,
    username = username,
    email = email,
    phone = phone,

    walletBalance = walletBalance,
    currency = currency,

    addresses = addresses,
    country = country,

    permission = permission,
)


fun List<DetailedUser>.toEntity(
    walletBalance: Double, currency: String, addresses: List<Address>, country: String, permission: Int
) = map { it.toEntity(walletBalance, currency, addresses, country, permission) }

fun List<DetailedUser>.toUserEntity(
    walletBalance: Double, currency: String, addresses: List<Address>, country: String, permission: Int
): List<User> {
    return this.map { detailedUser ->
        detailedUser.toEntity(walletBalance, currency, addresses, country, permission)
    }
}
