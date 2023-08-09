package org.thechance.service_identity.domain.usecases.useraccount

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.ManagedUser
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.gateway.DataBaseGateway

interface IManageUserAccountUseCase {

    suspend fun createUser(user: User): Boolean

    suspend fun deleteUser(id: String): Boolean

    suspend fun updateUser(id: String, user: User): Boolean

    suspend fun getUsers(): List<ManagedUser>

    suspend fun getUser(id: String): User

}

@Single
class IManageUserAccountUseCaseImp(
    private val dataBaseGateway: DataBaseGateway,
) : IManageUserAccountUseCase {

    override suspend fun createUser(user: User): Boolean {
        return dataBaseGateway.createUser(user)
    }

    override suspend fun deleteUser(id: String): Boolean {
        return dataBaseGateway.deleteUser(id)
    }

    override suspend fun updateUser(id: String, user: User): Boolean {
        return dataBaseGateway.updateUser(id, user)
    }

    override suspend fun getUsers(): List<ManagedUser> {
        return dataBaseGateway.getUsers()
    }

    override suspend fun getUser(id: String): User {
        return dataBaseGateway.getUserById(id)
    }

}