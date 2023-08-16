package org.thechance.api_gateway.domain.entity

import org.thechance.api_gateway.data.model.identity.PermissionResource

data class UserManagement(
    val id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val permissions: List<PermissionResource> = emptyList()
)