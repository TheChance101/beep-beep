package domain.usecase

import domain.gateway.IUserGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.utils.InvalidPasswordException
import domain.utils.InvalidUsernameException

interface IManageAuthenticationUseCase {
    suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String
    ): Boolean

    suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean): Boolean
}

class ManageAuthenticationUseCase(
    private val remoteGateway: IUserGateway,
    private val localGateway: ILocalConfigurationGateway
) : IManageAuthenticationUseCase {

    override suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String
    ): Boolean {
        return remoteGateway.createUser(fullName, username, password, email)
    }

    override suspend fun loginUser(
        username: String,
        password: String,
        keepLoggedIn: Boolean
    ): Boolean {
        validateLoginFields(username, password)
        val session = remoteGateway.loginUser(username, password)
        localGateway.saveAccessToken(session.accessToken)
        localGateway.saveRefreshToken(session.refreshToken)
        localGateway.saveKeepMeLoggedInFlag(keepLoggedIn)
        return true
    }

    private fun validateLoginFields(username: String, password: String) {
        if (username.isEmpty() || "[a-zA-Z0-9_]+".toRegex().matches(username).not()) {
            throw InvalidUsernameException()
        }
        if (password.isEmpty() || password.length < 8) {
            throw InvalidPasswordException()
        }
    }

}