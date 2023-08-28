package org.thechance.api_gateway.data.mappers

import org.thechance.api_gateway.data.model.identity.UserManagementResource
import org.thechance.api_gateway.endpoints.model.UserManagement

fun UserManagementResource.toManagedUser(): UserManagement {
    return UserManagement(
        id = this.id,
        fullName = this.fullName,
        username = this.username,
        email = this.email
    )
}