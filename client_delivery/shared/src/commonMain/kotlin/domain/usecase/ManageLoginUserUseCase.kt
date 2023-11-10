package domain.usecase

import domain.entity.DeliveryRequestPermission
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.remote.IIdentityRemoteGateway
import domain.utils.InvalidPasswordException
import domain.utils.InvalidUsernameException

interface IManageLoginUserUseCase {
    suspend fun loginUser(userName: String, password: String, isKeepMeLoggedInChecked: Boolean)
    suspend fun getKeepMeLoggedInFlag(): Boolean
    suspend fun requestPermission(deliveryRequestPermission: DeliveryRequestPermission): Boolean
    suspend fun saveUsername(username: String)
    suspend fun getUsername(): String
}

class ManageLoginUserUseCase(
    private val remoteGateway: IIdentityRemoteGateway,
    private val localGateWay: ILocalConfigurationGateway,
) : IManageLoginUserUseCase {

    override suspend fun loginUser(
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean,
    ) {
        if (validateLoginFields(userName, password)) {
            val userTokens = remoteGateway.loginUser(userName, password)
            localGateWay.saveAccessToken(userTokens.accessToken)
            localGateWay.saveRefreshToken(userTokens.refreshToken)
            localGateWay.saveKeepMeLoggedInFlag(isKeepMeLoggedInChecked)
            localGateWay.saveTaxiId(getTaxiId())
        }
    }

    override suspend fun getKeepMeLoggedInFlag(): Boolean {
        return localGateWay.getKeepMeLoggedInFlag()
    }

    private fun validateLoginFields(username: String, password: String): Boolean {
        if (username.isEmpty() || "[a-zA-Z0-9_]+".toRegex().matches(username).not()) {
            throw InvalidUsernameException("")
        } else if (password.isEmpty() || password.length < 8) {
            throw InvalidPasswordException("")
        }
        return true
    }

    override suspend fun requestPermission(deliveryRequestPermission: DeliveryRequestPermission): Boolean {
        return remoteGateway.createRequestPermission(
            deliveryRequestPermission
        )
    }

    override suspend fun saveUsername(username: String) {
        localGateWay.saveUserName(username)
    }

    override suspend fun getUsername(): String {
        return localGateWay.getUsername()
    }

    private suspend fun getTaxiId(): String {
        return remoteGateway.getAllVehicles().first().id
    }

}