package org.thechance.common.domain.usecase

import org.thechance.common.domain.getway.IIdentityGateway
import org.thechance.common.domain.getway.IRemoteGateway

interface ILoginUserUseCase {

    suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean)

}

class LoginUserUseCase(
    private val identityGateway: IIdentityGateway,
    private val remoteGateway: IRemoteGateway
) : ILoginUserUseCase {

    override suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean) {
        val userTokens = remoteGateway.loginUser(username, password)
        identityGateway.saveAccessToken(userTokens.first)
        identityGateway.saveRefreshToken(userTokens.second)
        identityGateway.shouldUserKeptLoggedIn(keepLoggedIn)
    }

}