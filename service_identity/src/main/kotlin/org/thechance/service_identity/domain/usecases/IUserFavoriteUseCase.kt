package org.thechance.service_identity.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.IDataBaseGateway

interface IUserFavoriteUseCase {
    suspend fun getFavoriteRestaurants(userId: String): List<String>

    suspend fun addRestaurantsToFavorite(userId: String, restaurantId: String): Boolean

    suspend fun removeRestaurantsFromFavorite(userId: String, restaurantId: String): Boolean
}

@Single
class UserFavoriteUseCase(private val dataBaseGateway: IDataBaseGateway) : IUserFavoriteUseCase {
    override suspend fun getFavoriteRestaurants(userId: String): List<String> {
        return dataBaseGateway.getFavoriteRestaurants(userId)
    }

    override suspend fun addRestaurantsToFavorite(userId: String, restaurantId: String): Boolean {
        return dataBaseGateway.addToFavorite(userId, restaurantId)
    }

    override suspend fun removeRestaurantsFromFavorite(userId: String, restaurantId: String): Boolean {
        return dataBaseGateway.deleteFromFavorite(userId, restaurantId)
    }


}