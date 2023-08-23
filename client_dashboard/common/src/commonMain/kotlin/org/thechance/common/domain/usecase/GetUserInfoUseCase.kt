package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Admin
import org.thechance.common.domain.getway.IRemoteGateway

interface IGetUserInfoUseCase {
    fun getUserInfo():Admin
}

class GetUserInfoUseCase(
    private val remoteGateway: IRemoteGateway,
) : IGetUserInfoUseCase {
    override fun getUserInfo(): Admin {
        return remoteGateway.getUserData()
    }
}