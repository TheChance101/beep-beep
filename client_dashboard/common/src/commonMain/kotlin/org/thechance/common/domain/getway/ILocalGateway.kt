package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.UserTokens


interface ILocalGateway {

     suspend fun saveAccessToken(token: String)

     suspend fun saveRefreshToken(token: String)

     suspend fun getAccessToken(): String

     suspend fun getRefreshToken(): String

     suspend fun clearTokens()

     suspend fun loginUser(username: String, password: String): UserTokens

}