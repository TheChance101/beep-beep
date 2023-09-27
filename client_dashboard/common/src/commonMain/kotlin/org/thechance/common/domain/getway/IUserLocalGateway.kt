package org.thechance.common.domain.getway

import kotlinx.coroutines.flow.Flow

interface IUserLocalGateway {

    suspend fun saveAccessToken(token: String)

    suspend fun saveUserName(username: String)
    suspend fun saveCountryCode(languageCode: String)
    suspend fun getCountryCode(): String
    suspend fun getUserName(): String

    suspend fun saveRefreshToken(token: String)

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun clearConfiguration()

    suspend fun getThemeMode(): Flow<Boolean>

    suspend fun updateThemeMode(mode: Boolean)

    suspend fun createUserConfiguration()

    suspend fun refreshAccessToken(refreshToken: String): Pair<String, String>
}