package org.thechance.common.domain.usecase

import org.koin.core.component.KoinComponent
import org.thechance.common.domain.entity.Admin
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IRemoteGateway


interface IGetUserInfoUseCase {
    fun getUserInfo():Admin
}

class GetUserInfoUseCase(
    private val remoteGateway: IRemoteGateway,
) : IGetUserInfoUseCase,KoinComponent {
    override fun getUserInfo(): Admin {
        return remoteGateway.getUserData()
    }
}