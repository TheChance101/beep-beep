package domain.usecase

import domain.gateway.IUserRemoteGateway
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
    private val remoteGateway: IUserRemoteGateway,
    private val localGateway: ILocalConfigurationGateway,
    private val validation: IValidationUseCase,
) : IManageAuthenticationUseCase {

    override suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String
    ): Boolean {
        with(validation) {
            validateFullName(fullName); validateUsername(username); validatePassword(password)
            validateEmail(email)
        }
        return remoteGateway.createUser(fullName, username, password, email).name.isNotEmpty()
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