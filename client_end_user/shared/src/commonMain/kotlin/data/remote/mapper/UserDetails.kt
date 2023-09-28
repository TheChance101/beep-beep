package data.remote.mapper

import data.remote.model.AddressDto
import data.remote.model.UserDetailsDto
import domain.entity.Address
import domain.entity.UserDetails

fun UserDetailsDto.toEntity (): UserDetails {
    return UserDetails(
    id = id ?: "",
        country = fullName ?: "",
        currency = currency ?: "",
        email = email ?: "",
        fullName = fullName ?: "",
        permission = permission ?: 1,
        username = username ?: "",
        walletBalance = walletBalance ?: 0.0,
        addresses = addresses?.map { it!!.toEntity() } ?: emptyList(),
         phoneNumber=phoneNumber?:""
    )
}

fun AddressDto.toEntity (): Address {
    return Address(
         id = id ?: "",
        address = address ?: "",
        location = location?.toEntity()
    )
}