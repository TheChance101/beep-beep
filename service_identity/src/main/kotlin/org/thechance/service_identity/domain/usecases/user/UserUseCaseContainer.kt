package org.thechance.service_identity.domain.usecases.user

import org.koin.core.annotation.Single

@Single
data class UserUseCaseContainer (
    val addPermissionToUser: AddPermissionToUserUseCase,
    val removePermissionFromUser: RemovePermissionFromUserUseCase,
    val getDetailedUsers: GetDetailedUsersUseCase,
    val getUserPermissions: GetUserPermissionsUseCase
)