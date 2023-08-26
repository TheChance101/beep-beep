package domain.usecase

import domain.entity.Cuisine
import domain.entity.Meal
import domain.gateway.IRemoteGateway

interface IMangeCuisineUseCase {
    suspend fun getCuisineByRestaurantId(id: String): List<Cuisine>
    suspend fun getCuisines(): List<Cuisine>
    suspend fun getMealsByCuisineId(id: String): List<Meal>
}

class MangeCuisineUseCase(private val remoteCuisineGateway: IRemoteGateway) :
    IMangeCuisineUseCase {
    override suspend fun getCuisineByRestaurantId(id: String): List<Cuisine> {
        return remoteCuisineGateway.getCuisineByRestaurantId(id)
    }

    override suspend fun getMealsByCuisineId(id: String): List<Meal> {
        return remoteCuisineGateway.getMealsByCuisineId(id)
    }

    override suspend fun getCuisines(): List<Cuisine> {
        return remoteCuisineGateway.getCuisines()
    }
}
