package org.thechance.service_identity.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_identity.data.collection.UserCollection
import org.thechance.service_identity.data.collection.UserDetailsCollection
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.entity.Wallet
import org.thechance.service_identity.endpoints.model.DetailedUserDto
import org.thechance.service_identity.endpoints.model.UserDto
import org.thechance.service_identity.endpoints.model.WalletDto
import org.thechance.service_identity.endpoints.model.request.CreateUserRequest
import org.thechance.service_identity.endpoints.model.request.UpdateUserRequest

fun UserCollection.toEntity(wallet: Wallet = Wallet("", "", 0.0)): User {
    return User(
        id = id.toHexString(),
        fullName = fullName,
        username = username,
        password = password,
        email = "",
        wallet = wallet
    )
}

fun UserDto.toEntity(wallet: Wallet): User {
    return User(
        id = id,
        fullName = fullName,
        username = username,
        password = password,
        email = "",
        wallet = wallet
    )
}

fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        fullName = fullName,
        username = username,
        password = password,
    )
}

fun List<User>.toDto(): List<UserDto> {
    return map { it.toDto() }
}

fun User.toDetailedDto(walletDto: WalletDto): DetailedUserDto {
    return DetailedUserDto(
        id = id,
        fullName = fullName,
        username = username,
        password = password,
        wallet = walletDto,
        addresses = addresses,
        permissions = permissions
    )
}

fun User.toCollection(): UserCollection {
    return UserCollection(
        fullName = this.fullName,
        username = this.username,
        password = this.password,
    )
}

fun User.toDetailsCollection(userId: String): UserDetailsCollection {
    return UserDetailsCollection(
        userId = ObjectId(userId),
        email = email,
        walletCollection = wallet.toCollection(),
        addresses = addresses.map { ObjectId(it) },
        permissions = permissions.map { it }
    )
}

fun CreateUserRequest.toEntity(wallet: Wallet = Wallet("", "", 0.0)): User {
    return User(
        id = "",
        fullName = fullName,
        username = username,
        password = password,
        email = "",
        wallet = wallet
    )
}

fun UpdateUserRequest.toEntity(wallet: Wallet = Wallet("", "", 0.0)): User {
    return User(
        id = "",
        fullName = fullName,
        username = username,
        password = password,
        email = "",
        wallet = wallet
    )
}