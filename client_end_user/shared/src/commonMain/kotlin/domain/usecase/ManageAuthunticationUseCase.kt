package domain.usecase

import domain.entity.Account
import domain.entity.User
import domain.gateway.IUserGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.usecase.validation.IValidationUseCase
import kotlinx.coroutines.flow.Flow

interface IManageAuthenticationUseCase {
    suspend fun createUser(userCreation: Account): Boolean

    suspend fun loginUser(username: String, password: String, keepLoggedIn: Boolean): Boolean

    suspend fun removeAccessToken()

    suspend fun removeRefreshToken()

    suspend fun getAccessToken(): Flow<String>
}

class ManageAuthenticationUseCase(
    private val remoteGateway: IUserGateway,
    private val localGateway: ILocalConfigurationGateway,
    private val validation: IValidationUseCase,
) : IManageAuthenticationUseCase {

    override suspend fun createUser(account: Account): Boolean {
        with(account) {
            with(validation) {
                validateFullName(fullName); validateUsername(username); validatePassword(password)
                validateEmail(email); validatePhone(phone); validateAddress(address)
            }
        }
        return remoteGateway.createUser(account).fullName.isNotEmpty()
    }

    override suspend fun loginUser(
        username: String, password: String, keepLoggedIn: Boolean
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

    override suspend fun removeRefreshToken() {
        return localGateway.removeRefreshToken()
    }

    override suspend fun getAccessToken(): Flow<String> {
        return localGateway.getAccessTokenStream()
    }

}
