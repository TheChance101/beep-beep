package org.thechance.common.domain.gateway

interface IIdentityGateway {

    suspend fun loginUser(username: String, password: String): Pair<String, String>

    suspend fun saveRefreshToken(token: String)

    suspend fun saveAccessToken(token: String)

    suspend fun refreshTokens(refreshToken: String): Pair<String, String>

    suspend fun shouldUserKeptLoggedIn(keepLoggedIn: Boolean)

    suspend fun isUserKeptLoggedIn(): Boolean

    suspend fun createUserConfiguration()

}