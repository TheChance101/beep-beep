package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.UserTokens
import org.thechance.common.domain.getway.ILocalGateway
import org.thechance.common.domain.getway.IRemoteGateway

interface ILoginUserUseCase {

    suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean): UserTokens

}

class LoginUserUseCase(
    private val localGateway: ILocalGateway,
    private val remoteGateway: IRemoteGateway
) : ILoginUserUseCase {

    override suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean): UserTokens {
        localGateway.shouldUserKeptLoggedIn(keepLoggedIn)
        val userTokens = remoteGateway.loginUser(username, password)

        localGateway.saveAccessToken(userTokens.accessToken)
        localGateway.saveRefreshToken(userTokens.refreshToken)

        return userTokens
    }

}