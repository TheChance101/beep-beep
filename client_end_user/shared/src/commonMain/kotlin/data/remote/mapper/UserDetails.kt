package data.remote.mapper

import data.remote.model.UserDetailsDto
import domain.entity.Price
import domain.entity.User

fun UserDetailsDto.toEntity() = User(
    id = id ?: "",
    country = fullName ?: "",
    email = email ?: "",
    fullName = fullName ?: "",
    permission = permission ?: 1,
    username = username ?: "",
    wallet = Price(walletBalance ?: 0.0, currency ?: ""),
    addresses = addresses?.filterNotNull()?.map { it.toEntity() } ?: emptyList(),
    phoneNumber = phoneNumber ?: ""
)



