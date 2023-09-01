package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IRemoteGateway

interface IGetLatestRegisteredUsersUseCase {

    suspend fun getLatestRegisteredUsers(): List<User>

}

class GetLatestRegisteredUsersUseCase(private val remoteGateway: IRemoteGateway) :
    IGetLatestRegisteredUsersUseCase {

    override suspend fun getLatestRegisteredUsers(): List<User> {
        return remoteGateway.getLatestRegisteredUsers()
    }

}