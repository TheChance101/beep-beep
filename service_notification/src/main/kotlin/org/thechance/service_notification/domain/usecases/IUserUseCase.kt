package org.thechance.service_notification.domain.usecases

import org.thechance.service_notification.domain.model.User

interface IUserUseCase {
    suspend fun createUser(user: User): Boolean
    suspend fun getUsers(): List<User>
    suspend fun getUser(id:String): User
}