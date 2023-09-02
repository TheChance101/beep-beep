package domain.usecase

import domain.gateway.IUserGateway
import domain.utils.InvalidPasswordException
import domain.utils.InvalidUsernameException

interface IManageAuthenticationUseCase {
    suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String
    ): Boolean

    suspend fun loginUser(username: String, password: String): Boolean
}

class ManageAuthenticationUseCase(private val remoteGateway: IUserGateway) :
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
        }
        return true
    }

    private fun validateLoginFields(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            throw InvalidUsernameException()
        } else if (password.isEmpty()) {
            throw InvalidPasswordException()
        } else return true
    }

}