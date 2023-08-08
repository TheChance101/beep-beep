package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.domain.model.User

@Single
class UserManagementUseCase(private val userGateway: IDatabaseGateway): IUserManagementUseCase {


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

interface IUserManagementUseCase {
    suspend fun createUser(user: User): Boolean
    suspend fun getUser(id:String): User
    suspend fun addTokenToUser(id:String,token: String): Boolean
}