package domain.usecase

import data.gateway.remote.FakeRemoteGateway
import domain.entity.PreferredRide
import domain.entity.User
import domain.gateway.local.ILocalConfigurationGateway
import domain.utils.AuthorizationException
import data.local.mapper.toFormattedString
import data.local.mapper.toPreferredRide

interface IManageUserUseCase {

    suspend fun getUserWallet(): User
    suspend fun savePriceLevel(priceLevel: String)
    suspend fun getPriceLevel(): String
    suspend fun savePreferredRide(preferredRide: PreferredRide)
    suspend fun getPreferredRide(): PreferredRide

}

class ManageUserUseCase(
    private val localGateway: ILocalConfigurationGateway,
    private val remoteGateway: FakeRemoteGateway
) : IManageUserUseCase {

    override suspend fun getUserWallet(): User {
        return if (localGateway.getAccessToken().isNotEmpty()) {
            remoteGateway.getUsrWallet()
        } else {
            throw AuthorizationException.UnAuthorizedException
        }
    }

    override suspend fun savePriceLevel(priceLevel: String) {
        localGateway.savePriceLevel(priceLevel)
    }

    override suspend fun getPriceLevel(): String {
        return localGateway.getPriceLevel()
    }

    override suspend fun savePreferredRide(preferredRide: PreferredRide) {
        localGateway.savePreferredRideQuality(preferredRide.toFormattedString())
    }

    override suspend fun getPreferredRide(): PreferredRide {
        return localGateway.getPreferredRideQuality().toPreferredRide()
    }

}
