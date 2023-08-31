package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IRemoteGateway

interface IManageUsersUseCase {
    suspend fun getUserInfo(): String
    suspend fun getUsers(page: Int, numberOfUsers: Int): DataWrapper<User>


}

class ManageUsersUseCase(
    private val remoteGateway: IRemoteGateway,
) : IManageUsersUseCase {

    override suspend fun getUserInfo(): String {
        return remoteGateway.getUserData()
    }

    override suspend fun getUsers(page: Int, numberOfUsers: Int): DataWrapper<User> {
        return remoteGateway.getUsers(page, numberOfUsers)
    }
}