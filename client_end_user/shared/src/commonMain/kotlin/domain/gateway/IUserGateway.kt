package domain.gateway


import domain.entity.Session

interface IUserGateway {

    suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String
    ): Boolean

    suspend fun loginUser(username: String, password: String): Session

    suspend fun refreshAccessToken(refreshToken: String) : Pair<String,String>

}