package domain.gateway


import domain.entity.Tokens

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
    ): Tokens

}