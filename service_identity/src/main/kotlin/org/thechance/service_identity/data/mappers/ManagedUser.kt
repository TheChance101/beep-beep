package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.UserCollection
import org.thechance.service_identity.domain.entity.ManagedUser
import org.thechance.service_identity.endpoints.model.ManagedUserDto

fun UserCollection.toManagedEntity() = ManagedUser(
    id.toHexString(),
    fullName,
    username,
    email,
    permissions.toEntity()
)

fun List<UserCollection>.toManagedEntity() = map { it.toManagedEntity() }

fun ManagedUser.toDto() = ManagedUserDto(
    id,
    fullName,
    username,
    email,
    permissions.toDto()
)

fun List<ManagedUser>.toDto() = map { it.toDto() }