package org.thechance.common.domain.getway

import kotlinx.coroutines.flow.Flow

interface IIdentityGateway {

    suspend fun saveAccessToken(token: String)

    suspend fun saveRefreshToken(token: String)

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun clearConfiguration()

    suspend fun shouldUserKeptLoggedIn(keepLoggedIn: Boolean)

    suspend fun isUserKeptLoggedIn(): Boolean

    suspend fun getThemeMode(): Flow<Boolean>

    suspend fun updateThemeMode(mode: Boolean)

    suspend fun createUserConfiguration()

    suspend fun refreshAccessToken(refreshToken: String): Pair<String, String>
}