package org.thechance.common.domain.usecase

import org.thechance.common.domain.getway.IUserLocalGateway

interface ILogoutUserUseCase {

    suspend fun logoutUser()

}

class LogoutUserUseCase(private val userLocalGateway: IUserLocalGateway) : ILogoutUserUseCase {

    override suspend fun logoutUser() {
        userLocalGateway.clearConfiguration()
    }

}