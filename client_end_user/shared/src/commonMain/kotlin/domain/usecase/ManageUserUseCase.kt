package domain.usecase

import data.gateway.fake.FakeRemoteGateway
import domain.entity.PreferredRide
import domain.entity.User
import domain.gateway.local.ILocalConfigurationGateway
import domain.utils.AuthorizationException
import data.local.mapper.toFormattedString
import data.local.mapper.toPreferredRide
import kotlinx.coroutines.flow.Flow
import domain.entity.UserDetails
import domain.gateway.IFakeRemoteGateway

interface IManageUserUseCase {

    suspend fun getUserWallet(): User
    suspend fun savePriceLevel(priceLevel: String)
    suspend fun getPriceLevel(): String
    suspend fun savePreferredRide(preferredRide: PreferredRide)
    suspend fun getPreferredRide(): PreferredRide

    suspend fun saveIsFirstTimeUseApp(isFirstTimeUseApp: Boolean)

    suspend fun getIsFirstTimeUseApp(): Boolean
    suspend fun getUserLanguageCode(): Flow<String>

    suspend fun getUserProfile(): UserDetails

}

class ManageUserUseCase(
    private val localGateway: ILocalConfigurationGateway,
    private val fakeRemoteGateway: IFakeRemoteGateway
) : IManageUserUseCase {

    override suspend fun getUserWallet(): User {
        return if (localGateway.getAccessToken().isNotEmpty()) {
            fakeRemoteGateway.getUsrWallet()
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

    override suspend fun saveIsFirstTimeUseApp(isFirstTimeUseApp: Boolean) {
        return localGateway.saveIsFirstTimeUseApp(isFirstTimeUseApp)
    }

    override suspend fun getIsFirstTimeUseApp(): Boolean {
        return localGateway.getIsFirstTimeUseApp()
    }

    override suspend fun getUserLanguageCode(): Flow<String> {
        return localGateway.getLanguageCodeFlow()
    }

    override suspend fun getUserProfile(): UserDetails {
       return fakeRemoteGateway.getUserProfile()
    }


}
