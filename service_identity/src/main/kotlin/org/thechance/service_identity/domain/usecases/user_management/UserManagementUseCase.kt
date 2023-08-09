package org.thechance.service_identity.domain.usecases.user_management

import org.thechance.service_identity.domain.entity.ManagedUser
import org.thechance.service_identity.domain.entity.Permission

interface UserManagementUseCase {

    suspend fun addPermissionToUser(userId: String, permissionId: Int): Boolean

    suspend fun removePermissionFromUser(userId: String, permissionId: Int): Boolean

    suspend fun getUserPermissions(userId: String): List<Permission>

    suspend fun getUsers(fullName: String, username: String): List<ManagedUser>

}