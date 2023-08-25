package org.thechance.common.domain.getway

import java.io.File


interface ILocalGateway {

     suspend fun saveTaxiReport(file: File)

     suspend fun saveAccessToken(token: String)

     suspend fun saveRefreshToken(token: String)

     suspend fun getAccessToken(): String

     suspend fun getRefreshToken(): String

     suspend fun clearTokens()

     suspend fun shouldUserKeptLoggedIn(keepLoggedIn: Boolean)

    suspend fun isUserKeptLoggedIn(): Boolean

    suspend fun getAllConfiguration()

}