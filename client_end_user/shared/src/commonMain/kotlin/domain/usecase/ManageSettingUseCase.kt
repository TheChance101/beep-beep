package domain.usecase

import data.local.mapper.toFormattedString
import data.local.mapper.toPreferredRide
import domain.entity.PreferredRide
import domain.entity.User
import domain.gateway.local.ILocalConfigurationGateway
import kotlinx.coroutines.flow.Flow

interface IManageSettingUseCase {
    suspend fun savePreferredFood(food: List<String>)
    suspend fun getPreferredFood(): List<String>?
    suspend fun saveLanguageCode(code: String)
    suspend fun getLanguageCode(): Flow<String>
    suspend fun savePriceLevel(priceLevel: String)
    suspend fun getPriceLevel(): String
    suspend fun savePreferredRide(preferredRide: PreferredRide)
    suspend fun getPreferredRide(): PreferredRide
    suspend fun saveIsFirstTimeUseApp(isFirstTimeUseApp: Boolean)
    suspend fun getIsFirstTimeUseApp(): Boolean
    suspend fun getUserLanguageCode(): Flow<String>
}

class ManageSettingUseCase(private val localGateway: ILocalConfigurationGateway) :
    IManageSettingUseCase {
    override suspend fun savePreferredFood(food: List<String>) {
        return localGateway.savePreferredFood(food)
    }

    override suspend fun getPreferredFood(): List<String>? {
        return localGateway.getPreferredFood()
    }

    override suspend fun saveLanguageCode(code: String) {
        return localGateway.saveLanguageCode(code)
    }

    override suspend fun getLanguageCode(): Flow<String> {
        return localGateway.getLanguageCodeFlow()
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
}
