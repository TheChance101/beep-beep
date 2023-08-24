package org.thechance.common.domain.usecase

import org.thechance.common.domain.getway.ILocalGateway

interface ILogoutUserUseCase {

    suspend fun logoutUser()

}

class LogoutUserUseCase(private val localGateway: ILocalGateway): ILogoutUserUseCase{

    override suspend fun logoutUser() {
        localGateway.clearTokens()
    }

}