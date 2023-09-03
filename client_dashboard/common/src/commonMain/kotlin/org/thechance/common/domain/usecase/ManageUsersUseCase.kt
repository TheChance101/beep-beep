package org.thechance.common.domain.usecase

import org.thechance.common.domain.getway.IRemoteGateway

interface IManageUsersUseCase {
    suspend fun getUserInfo(): String


}

class ManageUsersUseCase(
    private val remoteGateway: IRemoteGateway,
) : IManageUsersUseCase {

    override suspend fun getUserInfo(): String {
        return remoteGateway.getUserData()
    }

}