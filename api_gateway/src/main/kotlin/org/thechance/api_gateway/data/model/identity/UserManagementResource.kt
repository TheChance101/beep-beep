package org.thechance.api_gateway.data.model.identity

/**
 * Created by Aziza Helmy on 8/14/2023.
 */
data class UserManagementResource(
    val id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val permissions: List<PermissionResource> = emptyList()
)