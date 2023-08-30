package domain.usecase

import domain.entity.Session
import domain.gateway.IRemoteGateway
import domain.utils.InvalidPasswordException
import domain.utils.InvalidUsernameException
import domain.utils.UnAuthorizedException

interface IManageAuthenticationUseCase {
    suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String
    ): Boolean

    suspend fun loginUser(username: String, password: String): Boolean
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

    override suspend fun loginUser(username: String, password: String): Boolean {
        return true
//        println(" token is  ${remoteGateway.loginUser(username, password)}")
//        return if (validateLoginFields(username, password)) {
//            val session = remoteGateway.loginUser(username, password)
//            session.accessToken.isNotEmpty()
//        } else {
//            false
//        }

    }


    private fun validateLoginFields(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            throw InvalidUsernameException()
        } else if (password.isEmpty()) {
            throw InvalidPasswordException()
        } else return true
    }

}