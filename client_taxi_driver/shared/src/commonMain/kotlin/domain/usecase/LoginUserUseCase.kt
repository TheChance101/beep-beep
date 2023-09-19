package domain.usecase

import domain.InvalidPasswordException
import domain.InvalidUserNameException
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
        restaurantName: String,
        ownerEmail: String,
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
        restaurantName: String, ownerEmail: String, description: String
    ): Boolean {
        return remoteGateway.createRequestPermission(
            restaurantName,
            ownerEmail,
            description
        )
    }


    private fun validateLoginFields(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            throw InvalidUserNameException("")
        } else if (password.isEmpty()) {
            throw InvalidPasswordException("")
        } else return true
    }

}