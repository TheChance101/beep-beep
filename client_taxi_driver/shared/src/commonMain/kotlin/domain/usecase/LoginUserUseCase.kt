package domain.usecase

import domain.*
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.remote.IIdentityRemoteGateway

interface ILoginUserUseCase {

    suspend fun loginUser(
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean
    )

    suspend fun getKeepMeLoggedInFlag(): Boolean

    suspend fun requestPermission(
        driverFullName: String,
        driverEmail: String,
        description: String
    )
}

class LoginUserUseCase(
    private val remoteGateway: IIdentityRemoteGateway,
    private val localGateWay: ILocalConfigurationGateway
) : ILoginUserUseCase {

    override suspend fun loginUser(
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean
    ) {
        checkValidationLoginFields(userName, password)
        val userTokens = remoteGateway.loginUser(userName, password)
        localGateWay.saveAccessToken(userTokens.accessToken)
        localGateWay.saveRefreshToken(userTokens.refreshToken)
        localGateWay.saveKeepMeLoggedInFlag(isKeepMeLoggedInChecked)
    }

    override suspend fun getKeepMeLoggedInFlag(): Boolean {
        return localGateWay.getKeepMeLoggedInFlag()
    }

    override suspend fun requestPermission(
        driverFullName: String, driverEmail: String, description: String
    ) {
        checkValidationPermissionFields(driverFullName, driverEmail, description)

        remoteGateway.createRequestPermission(
            driverFullName,
            driverEmail,
            description
        )
    }


    private fun checkValidationLoginFields(username: String, password: String) {
        when {
            username.isBlank() -> {
                throw InvalidUserNameException("")
            }

            password.isBlank() -> {
                throw InvalidPasswordException("")
            }
        }
    }

    private fun checkValidationPermissionFields(driverFullName: String, driverEmail: String, description: String) {
        when {
            driverFullName.isBlank() -> {
                throw InvalidDriverNameException("")
            }

            driverEmail.isBlank() -> {
                throw InvalidDriverEmailException("")
            }

            description.isBlank() -> {
                throw InvalidDescriptionException("")
            }
        }
    }
}