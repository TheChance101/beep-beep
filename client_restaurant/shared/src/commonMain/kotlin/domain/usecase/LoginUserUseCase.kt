package domain.usecase

import domain.entity.AddressInfo
import domain.entity.Location
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.remote.IIdentityRemoteGateway
import presentation.base.InvalidPasswordException
import presentation.base.InvalidUserNameException
import presentation.base.PermissionDenied

interface ILoginUserUseCase {

    suspend fun loginUser(
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean,
    )

    suspend fun getKeepMeLoggedInFlag(): Boolean
    suspend fun requestPermission(
        restaurantName: String,
        ownerEmail: String,
        description: String,
    ): Boolean

    suspend fun getRestaurantId(): String
    suspend fun saveRestaurantId(restaurantId: String)
    suspend fun saveRestaurantLocation(addressInfo: AddressInfo)
    suspend fun getNumberOfRestaurants(): Int

}

class LoginUserUseCase(
    private val remoteGateway: IIdentityRemoteGateway,
    private val localGateWay: ILocalConfigurationGateway,
) : ILoginUserUseCase {

    override suspend fun loginUser(
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean,
    ) {
        if (validateLoginFields(userName, password)) {
            val session = remoteGateway.loginUser(userName, password)
            localGateWay.saveAccessToken(session.accessToken)
            localGateWay.saveRefreshToken(session.refreshToken)
            localGateWay.saveKeepMeLoggedInFlag(isKeepMeLoggedInChecked)
        }
    }

    override suspend fun getKeepMeLoggedInFlag(): Boolean {
        return localGateWay.getKeepMeLoggedInFlag()
    }

    override suspend fun requestPermission(
        restaurantName: String, ownerEmail: String, description: String,
    ): Boolean {
        return remoteGateway.createRequestPermission(restaurantName, ownerEmail, description)
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
            throw PermissionDenied("You don't have permission to login")
        } else return true
    }

    override suspend fun getRestaurantId(): String {
        return localGateWay.getRestaurantId()
    }

    override suspend fun saveRestaurantId(restaurantId: String) {
        localGateWay.saveRestaurantId(restaurantId)
    }

    override suspend fun saveRestaurantLocation(addressInfo: AddressInfo) {
        localGateWay.saveRestaurantLocation(addressInfo)
    }

    override suspend fun getNumberOfRestaurants(): Int {
        return localGateWay.getNumberOfRestaurants()
    }

    companion object {
        private const val HAS_PERMISSION = "5"
        private const val NO_PERMISSION = "1"
    }
}
