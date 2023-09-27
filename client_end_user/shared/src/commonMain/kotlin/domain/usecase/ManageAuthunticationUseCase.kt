package domain.usecase

import domain.entity.UserCreation
import domain.gateway.IUserRemoteGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.usecase.validation.IValidationUseCase

interface IManageAuthenticationUseCase {
    suspend fun createUser(userCreation: UserCreation): Boolean

    suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean): Boolean

    suspend fun removeAccessToken()

    suspend fun  removeRefreshToken()

    suspend fun getAccessToken(): String
}

class ManageAuthenticationUseCase(
    private val remoteGateway: IUserRemoteGateway,
    private val localGateway: ILocalConfigurationGateway,
    private val validation: IValidationUseCase,
) : IManageAuthenticationUseCase {

    override suspend fun createUser(userCreation: UserCreation): Boolean {
        with(userCreation) {
            with(validation) {
                validateFullName(fullName); validateUsername(username); validatePassword(password)
                validateEmail(email); validatePhone(phone); validateAddress(address)
            }
        }
        return remoteGateway.createUser(userCreation).name.isNotEmpty()
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

    override suspend fun removeAccessToken() {
        return localGateway.removeAccessToken()
    }

    override suspend fun removeRefreshToken()  {
        return localGateway.removeRefreshToken()
    }

    override suspend fun getAccessToken(): String {
        return localGateway.getAccessToken()
    }

}