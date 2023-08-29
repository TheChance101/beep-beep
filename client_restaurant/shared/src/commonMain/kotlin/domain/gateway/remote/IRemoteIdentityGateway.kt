package domain.gateway.remote

import domain.entity.UserTokens


interface IRemoteIdentityGateway {

    suspend fun loginUser(userName: String, password: String): UserTokens



}