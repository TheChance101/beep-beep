package domain.usecase

import data.remote.model.RestaurantPermission
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.remote.IIdentityRemoteGateway
import io.ktor.util.decodeBase64Bytes
import kotlinx.serialization.json.Json
import presentation.base.InvalidPasswordException
import presentation.base.InvalidUserNameException
import presentation.base.PermissionDenied

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

            if (decodedToken(userTokens.accessToken)) {
                localGateWay.saveAccessToken(userTokens.accessToken)
                localGateWay.saveRefreshToken(userTokens.refreshToken)
                localGateWay.saveKeepMeLoggedInFlag(isKeepMeLoggedInChecked)
            }
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

    private fun decodedToken(input: String): Boolean {
        val elements = input.split('.')
        val payload = elements[1]
        val decryptionTokenValue = payload.decodeBase64Bytes().decodeToString()
        val result = parseToRestaurantPermission(decryptionTokenValue)

        return validatePermissionRestaurant(result.permission)
    }

    private fun parseToRestaurantPermission(decryptionTokenValue: String): RestaurantPermission {
        return Json.decodeFromString(decryptionTokenValue)
    }

    private fun validateLoginFields(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            throw InvalidUserNameException()
        } else if (password.isEmpty()) {
            throw InvalidPasswordException()
        } else return true
    }

    private fun validatePermissionRestaurant(permission: String?): Boolean {
        if (permission != HAS_PERMISSION) {
            throw PermissionDenied()
        } else return true
    }

    companion object {
        private const val HAS_PERMISSION = "5"
        private const val NO_PERMISSION = "1"
    }
}
