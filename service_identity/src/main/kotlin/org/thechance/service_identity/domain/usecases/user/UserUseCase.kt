package org.thechance.service_identity.domain.usecases.user

import org.thechance.service_identity.domain.entity.User

interface CreateUserUseCase {
    suspend fun invoke(user: User): Boolean
}

interface UpdateUserUseCase {
    suspend fun invoke(id: String, user: User): Boolean
}

interface GetUsersUseCase {
    suspend fun invoke(): List<User>
}

interface GetUserByIdUseCase {
    suspend fun invoke(id: String): User
}

interface DeleteUserUseCase {
    suspend fun invoke(id: String): Boolean
}