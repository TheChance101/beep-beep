package domain.usecase

import data.local.mapper.toFormattedString
import data.local.mapper.toPreferredRide
import domain.entity.PreferredRide
import domain.gateway.local.ILocalConfigurationGateway
import kotlinx.coroutines.flow.Flow

interface IMangeUserPreferenceUseCase {
    suspend fun savePreferredFood(food: List<String>)
    suspend fun getPreferredFood(): List<String>?
    suspend fun saveLanguageCode(code: String)
    suspend fun getLanguageCode(): Flow<String>
    suspend fun savePriceLevel(priceLevel: String)
    suspend fun getPriceLevel(): String
    suspend fun savePreferredRide(preferredRide: PreferredRide)
    suspend fun getPreferredRide(): PreferredRide
}

class MangeUserPreferenceUseCase(
    private val localGateway: ILocalConfigurationGateway
) : IMangeUserPreferenceUseCase {
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
}