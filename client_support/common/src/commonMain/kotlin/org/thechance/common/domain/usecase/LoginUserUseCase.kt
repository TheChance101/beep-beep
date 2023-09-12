package org.thechance.common.domain.usecase

import org.thechance.common.domain.gateway.IIdentityGateway


interface ILoginUserUseCase {

    suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean)

}

class LoginUserUseCase(private val identity: IIdentityGateway) : ILoginUserUseCase {

    override suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean) {
        val (accessToken, refreshToken) = identity.loginUser(username, password)
        identity.createUserConfiguration()
        identity.saveAccessToken(accessToken)
        identity.saveRefreshToken(refreshToken)
        identity.shouldUserKeptLoggedIn(keepLoggedIn)
    }

}