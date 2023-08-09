package org.thechance.service_identity.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_identity.data.collection.UpdateUserCollection
import org.thechance.service_identity.data.collection.UserCollection
import org.thechance.service_identity.data.collection.UserDetailsCollection
import org.thechance.service_identity.data.collection.WalletCollection
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.endpoints.model.DetailedUserDto
import org.thechance.service_identity.endpoints.model.UserDto
import org.thechance.service_identity.endpoints.model.WalletDto
import org.thechance.service_identity.endpoints.model.request.CreateUserRequest
import org.thechance.service_identity.endpoints.model.request.UpdateUserRequest

fun UserCollection.toEntity(): User {
    return User(
        id = id.toHexString(),
        fullName = fullName,
        username = username,
        password = password,
        email = "",
    )
}

fun List<UserCollection>.tEntity(): List<User> {
    return this.map { it.toEntity() }
}

fun User.toUpdateCollection(): UpdateUserCollection {
    return UpdateUserCollection(
        fullName = fullName.ifBlank { null },
        username = username.ifBlank { null },
        password = password.ifBlank { null },
    )
}

fun UserDto.toEntity(): User {
    return User(
        id = id,
        fullName = fullName,
        username = username,
        password = password,
        email = "",
    )
}

fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        fullName = fullName,
        username = username,
        password = password,
        email = "",
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
    )
}

fun User.toDetailsCollection(userId: String): UserDetailsCollection {
    return UserDetailsCollection(
        userId = ObjectId(userId),
        email = email,
        walletCollection = WalletCollection(userId = userId, walletBalance = walletBalance),
        addresses = addresses.map { ObjectId(it.id) },
        permissions = permissions.map { it.id }
    )
}

fun CreateUserRequest.toEntity(): User {
    return User(
        id = "",
        fullName = fullName,
        username = username,
        password = password,
        email = "",
    )
}

fun UpdateUserRequest.toEntity(): User {
    return User(
        id = "",
        fullName = fullName ?: "",
        username = username ?: "",
        password = password ?: "",
        email = ""
    )
}