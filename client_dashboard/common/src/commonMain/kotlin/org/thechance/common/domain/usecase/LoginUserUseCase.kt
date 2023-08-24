package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.UserTokens
import org.thechance.common.domain.getway.ILocalGateway
import org.thechance.common.domain.getway.IRemoteGateway

interface ILoginUserUseCase {

    suspend fun saveAccessToken(token: String)

    suspend fun saveRefreshToken(token: String)

    suspend fun getAccessToken(): String

    suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean): UserTokens

}

class LoginUserUseCase(
    private val localGateway: ILocalGateway,
    private val remoteGateway: IRemoteGateway
) : ILoginUserUseCase {

    override suspend fun saveAccessToken(token: String) {
        localGateway.saveAccessToken(token)
    }

    override suspend fun saveRefreshToken(token: String) {
        localGateway.saveRefreshToken(token)
    }

    override suspend fun getAccessToken(): String {
        return localGateway.getAccessToken()
    }

    override suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean): UserTokens {
        localGateway.shouldUserKeptLoggedIn(keepLoggedIn)
        return remoteGateway.loginUser(username, password)
    }

}