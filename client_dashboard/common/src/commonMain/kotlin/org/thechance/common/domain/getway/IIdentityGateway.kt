package org.thechance.common.domain.getway

interface IIdentityGateway {

    suspend fun saveAccessToken(token: String)

    suspend fun saveRefreshToken(token: String)

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun clearTokens()

    suspend fun shouldUserKeptLoggedIn(keepLoggedIn: Boolean)

    suspend fun isUserKeptLoggedIn(): Boolean

}