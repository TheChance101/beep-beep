package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.UserTokens
import org.thechance.common.domain.getway.ILocalGateway

interface ILoginUserUseCase {

    suspend fun saveAccessToken(token: String)

    suspend fun saveRefreshToken(token: String)

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun loginUser(username: String, password: String): UserTokens

}

class LoginUserUseCase(private val localGateway: ILocalGateway): ILoginUserUseCase {

    override suspend fun saveAccessToken(token: String) {
        localGateway.saveAccessToken(token)
    }

    override suspend fun saveRefreshToken(token: String) {
        localGateway.saveRefreshToken(token)
    }

    override suspend fun getAccessToken(): String {
        return localGateway.getAccessToken()
    }

    override suspend fun getRefreshToken(): String {
        return localGateway.getRefreshToken()
    }

    override suspend fun loginUser(username: String, password: String): UserTokens {
        return localGateway.loginUser(username, password)
    }

}