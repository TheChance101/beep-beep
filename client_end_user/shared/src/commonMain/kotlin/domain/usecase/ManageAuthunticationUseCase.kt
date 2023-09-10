package domain.usecase

import domain.gateway.IUserGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.usecase.validation.IValidationUseCase

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
    private val localGateway: ILocalConfigurationGateway,
    private val validation: IValidationUseCase,
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
        validation.validateUsername(username); validation.validatePassword(password)
        val session = remoteGateway.loginUser(username, password)
        localGateway.saveAccessToken(session.accessToken)
        localGateway.saveRefreshToken(session.refreshToken)
        localGateway.saveKeepMeLoggedInFlag(keepLoggedIn)
        return true
    }

}