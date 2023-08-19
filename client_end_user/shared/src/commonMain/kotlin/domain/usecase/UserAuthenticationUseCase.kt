package domain.usecase

import domain.entity.Tokens
import domain.gateway.IRemoteGateway

class UserAuthenticationUseCase (private val remoteGateway: IRemoteGateway) : IUserAuthenticationUseCase {

    override suspend fun createUser(fullName: String, username: String, password: String, email: String): Boolean {
        return remoteGateway.createUser(fullName, username, password, email)
    }

    override suspend fun loginUser(userName: String, password: String) {
         val tokens = remoteGateway.loginUser(userName, password)
        //save tokens to shared Preferences
    }

}

interface IUserAuthenticationUseCase{

    suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String
    ): Boolean

    suspend fun loginUser(
        userName: String,
        password: String,
    )

}