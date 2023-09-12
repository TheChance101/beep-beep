package domain.gateway


import domain.entity.Session
import domain.entity.User

interface IUserRemoteGateway {

    suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String
    ): User

    suspend fun loginUser(username: String, password: String): Session

    suspend fun refreshAccessToken(refreshToken: String) : Pair<String,String>

}