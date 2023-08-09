package org.thechance.service_identity.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_identity.data.collection.*
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.endpoints.model.DetailedUserDto
import org.thechance.service_identity.endpoints.model.UserDto
import org.thechance.service_identity.endpoints.model.WalletDto

fun UserCollection.toEntity(): User {
    return User(
        id = id.toHexString(),
        fullName = fullName,
        username = username,
        password = password ?: "",
        email = email,
        permissions = permissions.toEntity()
    )
}

fun List<UserCollection>.toEntity(): List<User> {
    return this.map { it.toEntity() }
}

fun User.toUpdateRequest(): UpdateUserRequest {
    return UpdateUserRequest(
        fullName = fullName.ifBlank { null },
        username = username.ifBlank { null },
        password = password.ifBlank { null },
        email = email.ifBlank { null },
    )
}

fun UserDto.toEntity(): User {
    return User(
        id = id,
        fullName = fullName,
        username = username,
        password = password,
        email = email,
    )
}

fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        fullName = fullName,
        username = username,
        password = password,
        email = email,
        walletBalance = walletBalance,
        addresses = addresses.toDto(),
        permissions = permissions.toDto()
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
        email = email,
        wallet = walletDto,
        addresses = addresses.toDto(),
        permissions = permissions.toDto()
    )
}

fun User.toCollection(): UserCollection {
    return UserCollection(
        fullName = fullName,
        username = username,
        password = password,
        email = email,
        permissions = permissions.toCollection()
    )
}

fun User.toDetailsCollection(userId: String): UserDetailsCollection {
    return UserDetailsCollection(
        userId = ObjectId(userId),
        walletCollection = WalletCollection(userId = userId, walletBalance = walletBalance),
        addresses = addresses.map { ObjectId(it.id) },
    )
}

fun CreateUserRequest.toEntity(): User {
    return User(
        id = "",
        fullName = fullName,
        username = username,
        password = password,
        email = email,
    )
}

fun UpdateUserRequest.toEntity(): User {
    return User(
        id = "",
        fullName = fullName ?: "",
        username = username ?: "",
        password = password ?: "",
        email = email ?: "",
    )
}