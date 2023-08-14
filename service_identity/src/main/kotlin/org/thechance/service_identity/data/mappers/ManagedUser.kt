package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.UserCollection
import org.thechance.service_identity.domain.entity.UserManagement
import org.thechance.service_identity.endpoints.model.UserManagementDto

fun UserCollection.toManagedEntity() = UserManagement(
    id.toHexString(),
    fullName,
    username,
    email,
    permissions.toEntity()
)

fun List<UserCollection>.toManagedEntity() = map { it.toManagedEntity() }

fun UserManagement.toDto() = UserManagementDto(
    id,
    fullName,
    username,
    email,
    permissions.toDto()
)

fun List<UserManagement>.toDto() = map { it.toDto() }