package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.CreateUserDocument
import org.thechance.service_identity.data.collection.UpdateUserDocument
import org.thechance.service_identity.data.collection.UserCollection
import org.thechance.service_identity.domain.entity.CreateUserRequest
import org.thechance.service_identity.domain.entity.UpdateUserRequest
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.endpoints.model.UserDto

fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        fullName = fullName,
        username = username,
        email = email,
        walletBalance = walletBalance,
        addresses = addresses.toDto(),
        permissions = permissions.toDto()
    )
}

fun CreateUserDocument.toCreateRequest(): CreateUserRequest = CreateUserRequest(
    fullName = fullName,
    username = username,
    password = password,
    email = email,
)

fun UpdateUserDocument.toUpdateRequest(): UpdateUserRequest = UpdateUserRequest(
    fullName = fullName,
    username = username,
    password = password,
    email = email,
)

fun CreateUserRequest.toCollection(): UserCollection {
    return UserCollection(
        fullName = fullName,
        username = username,
        password = password,
        email = email,
    )
}

fun UpdateUserRequest.toUpdateRequest(): UpdateUserDocument {
    return UpdateUserDocument(
        fullName = fullName,
        username = username,
        password = password,
        email = email,
    )
}