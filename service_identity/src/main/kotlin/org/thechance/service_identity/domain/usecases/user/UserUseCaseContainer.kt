package org.thechance.service_identity.domain.usecases.user

import org.koin.core.annotation.Single

@Single
data class UserUseCaseContainer (
    val getUserByIdUseCase: GetUserByIdUseCase,
    val getUsersUseCase: GetUsersUseCase,
    val deleteUserUseCase: DeleteUserUseCase,
    val updateUserUseCase: UpdateUserUseCase,
)