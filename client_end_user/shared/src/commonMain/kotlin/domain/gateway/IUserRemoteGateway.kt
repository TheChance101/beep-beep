package domain.gateway


import domain.entity.Session
import domain.entity.User
import domain.entity.UserCreation

interface IUserRemoteGateway {

    suspend fun createUser(userCreation: UserCreation): User

    suspend fun loginUser(username: String, password: String): Session

    suspend fun refreshAccessToken(refreshToken: String) : Pair<String,String>

}