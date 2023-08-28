package org.thechance.common.domain.usecase

import org.thechance.common.domain.getway.IRemoteGateway

interface IGetUserInfoUseCase {
    fun getUserInfo(): String
}

class GetUserInfoUseCase(
    private val remoteGateway: IRemoteGateway,
) : IGetUserInfoUseCase {
    override fun getUserInfo(): String {
        return remoteGateway.getUserData()
    }
}