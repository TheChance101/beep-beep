package org.thechance.service_identity.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_identity.api.model.UserDto
import org.thechance.service_identity.api.model.request.UpdateUserRequest
import org.thechance.service_identity.data.collection.UserCollection
import org.thechance.service_identity.data.collection.UserDetailsCollection
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.endpoints.model.DetailedUserDto
import org.thechance.service_identity.endpoints.model.request.CreateUserRequest

fun UserCollection.toEntity(): User {
    return User(
        id = id.toHexString(),
        fullName = fullName,
        username = username,
        password = password,
        isDeleted = isDeleted
    )
}

fun UserDto.toEntity(): User {
    return User(
        id = id,
        fullName = fullName,
        username = username,
        password = password,
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

fun User.toDetailedDto(): DetailedUserDto {
    return DetailedUserDto(
        id = id,
        fullName = fullName,
        username = username,
        password = password,
        email = email,
        walletId = walletId,
        addresses = addresses,
        permissions = permissions
    )
}

fun User.toCollection(): UserCollection {
    return UserCollection(
        fullName = this.fullName,
        username = this.username,
        password = this.password,
        isDeleted = this.isDeleted,
    )
}

fun User.toDetailsCollection(userId: String): UserDetailsCollection {
    return UserDetailsCollection(
        userId = ObjectId(userId),
        email = email,
        walletId = walletId,
        addresses = addresses.map { ObjectId(it) },
        permissions = permissions.map { it }
    )
}

fun CreateUserRequest.toEntity(): User {
    return User(
        fullName = fullName,
        username = username,
        password = password
    )
}

fun UpdateUserRequest.toEntity(): User {
    return User(
        fullName = fullName,
        username = username,
        password = password,
        email = email
    )
}