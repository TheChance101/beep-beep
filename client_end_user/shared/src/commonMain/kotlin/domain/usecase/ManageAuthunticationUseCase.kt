package domain.usecase

import domain.gateway.IRemoteGateway

interface IManageAuthenticationUseCase {
    suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String
    ): Boolean

    suspend fun loginUser(userName: String, password: String)
}

class ManageAuthenticationUseCase(private val remoteGateway: IRemoteGateway) :
    IManageAuthenticationUseCase {

    override suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String
    ): Boolean {
        return remoteGateway.createUser(fullName, username, password, email)
    }

    override suspend fun loginUser(userName: String, password: String) {
        val tokens = remoteGateway.loginUser(userName, password)
        //save tokens to shared Preferences
    }

}