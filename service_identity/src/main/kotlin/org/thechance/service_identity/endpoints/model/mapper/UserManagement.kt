package org.thechance.service_identity.endpoints.model.mapper

import org.thechance.service_identity.domain.entity.UserManagement
import org.thechance.service_identity.endpoints.model.UserManagementDto

fun UserManagement.toDto() = UserManagementDto(
    id,
    fullName,
    username,
    email,
    country,
    permission
)

fun List<UserManagement>.toDto() = map { it.toDto() }