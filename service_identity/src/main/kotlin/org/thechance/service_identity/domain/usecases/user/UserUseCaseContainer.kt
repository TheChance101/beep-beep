package org.thechance.service_identity.domain.usecases.user

import org.koin.core.annotation.Single

@Single
data class UserUseCaseContainer (
    val createUserUseCase: CreateUserUseCase,
    val getUserByIdUseCase: GetUserByIdUseCase,
    val getUsersUseCase: GetUsersUseCase,
    val deleteUserUseCase: DeleteUserUseCase,
    val updateUserUseCase: UpdateUserUseCase,
    val addPermissionToUser: AddPermissionToUserUseCase,
    val removePermissionFromUser: RemovePermissionFromUserUseCase,
    val getDetailedUsers: GetDetailedUsersUseCase,
    val getUserPermissions: GetUserPermissionsUseCase
)