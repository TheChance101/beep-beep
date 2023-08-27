package org.thechance.common.domain.usecase

import org.thechance.common.domain.getway.IIdentityGateway

interface ILogoutUserUseCase {

    suspend fun logoutUser()

}

class LogoutUserUseCase(private val identityGateway: IIdentityGateway) : ILogoutUserUseCase {

    override suspend fun logoutUser() {
        identityGateway.clearTokens()
    }

}