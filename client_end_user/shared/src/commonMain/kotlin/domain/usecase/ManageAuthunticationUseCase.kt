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
        if (validateLoginFields(username, password)) {
            remoteGateway.loginUser(username, password)
        } else {
            throw UnAuthorizedException()
        }
        return true
//        return if (validateLoginFields(username, password)) {
//            remoteGateway.loginUser(username, password)
//            true
//        } else {
//            throw UnAuthorizedException()
//        }
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