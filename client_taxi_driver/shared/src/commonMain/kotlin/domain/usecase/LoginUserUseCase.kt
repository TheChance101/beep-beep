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
    ): Boolean
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
        if (validateLoginFields(userName, password)) {
            val userTokens = remoteGateway.loginUser(userName, password)
            localGateWay.saveAccessToken(userTokens.accessToken)
            localGateWay.saveRefreshToken(userTokens.refreshToken)
            localGateWay.saveKeepMeLoggedInFlag(isKeepMeLoggedInChecked)
        }
    }

    override suspend fun getKeepMeLoggedInFlag(): Boolean {
        return localGateWay.getKeepMeLoggedInFlag()
    }

    override suspend fun requestPermission(
        driverFullName: String, driverEmail: String, description: String
    ): Boolean {
        return if (validatePermissionFields(driverFullName, driverEmail, description)) {
             remoteGateway.createRequestPermission(
                driverFullName,
                driverEmail,
                description
            )
        } else false
    }


    private fun validateLoginFields(username: String, password: String): Boolean {
        if (username.isBlank()) {
            throw InvalidUserNameException("")
        } else if (password.isBlank()) {
            throw InvalidPasswordException("")
        } else return true
    }

    private fun validatePermissionFields(driverFullName: String, driverEmail: String, description: String): Boolean {
        if (driverFullName.isBlank()) {
            throw InvalidDriverNameException("")
        } else if (driverEmail.isBlank()) {
            throw InvalidDriverEmailException("")
        } else if (description.isBlank()) {
            throw InvalidDescriptionException("")
        } else return true
    }

}