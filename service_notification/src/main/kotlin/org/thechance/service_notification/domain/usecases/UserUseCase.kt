package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IUserGateway
import org.thechance.service_notification.domain.model.User

@Single
class UserUseCase(private val userGateway: IUserGateway): IUserUseCase {
    override suspend fun getUsers(): List<User> {
        return userGateway.getUsers()
    }

    override suspend fun getUser(id: String): User {
        return userGateway.getUser(id)
    }

    override suspend fun addTokenToUser(id: String,token: String): Boolean {
        return userGateway.addTokenToUser(id,token)
    }

    override suspend fun createUser(user: User): Boolean {
        return userGateway.createUser(user)
    }

}

interface IUserUseCase {
    suspend fun createUser(user: User): Boolean
    suspend fun getUsers(): List<User>
    suspend fun getUser(id:String): User
    suspend fun addTokenToUser(id:String,token: String): Boolean
}