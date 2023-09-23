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
        checkValidationPermissionFields(driverFullName, driverEmail)

        remoteGateway.createRequestPermission(
            driverFullName,
            driverEmail,
            description
        )
    }


    private fun checkValidationLoginFields(username: String, password: String) {
        val validUserNameRegex = "[a-zA-Z0-9_]+".toRegex()
        when {
            username.isBlank() -> throw InvalidUserNameException("username cannot be empty")
            !(username.matches(validUserNameRegex)) -> throw InvalidUserNameException("Invalid username")
        }
        when {
            password.isBlank() -> throw InvalidPasswordException("password cannot be empty")
            password.length < 8 -> throw InvalidPasswordException("Invalid password")
        }
    }

    private fun checkValidationPermissionFields(driverFullName: String, driverEmail: String) {
        val validEmailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()

        if (driverFullName.isBlank()) throw InvalidDriverNameException("FullName cannot be empty")

        when {
            driverEmail.isBlank() -> throw InvalidDriverEmailException("Email cannot be empty")

            !(driverEmail.matches(validEmailRegex)) -> throw InvalidDriverEmailException("Invalid Email")
        }
    }
}