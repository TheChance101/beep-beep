package org.thechance.common.domain.usecase

import org.thechance.common.domain.getway.ILocalGateway
import org.thechance.common.domain.getway.IRemoteGateway

interface ILoginUserUseCase {

    suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean)

}

class LoginUserUseCase(
    private val localGateway: ILocalGateway,
    private val remoteGateway: IRemoteGateway
) : ILoginUserUseCase {

    override suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean) {
        val userTokens = remoteGateway.loginUser(username, password)
        localGateway.saveAccessToken(userTokens.first)
        localGateway.saveRefreshToken(userTokens.second)

        localGateway.shouldUserKeptLoggedIn(keepLoggedIn)
    }

}