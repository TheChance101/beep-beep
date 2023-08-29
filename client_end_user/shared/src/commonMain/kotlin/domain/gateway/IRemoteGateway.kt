package domain.gateway


import domain.entity.UserTokens

interface IRemoteGateway {

    suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String
    ) : Boolean

    suspend fun loginUser(
        userName: String,
        password: String,
    ): UserTokens

}