package domain.usecase

import domain.entity.Cuisine
import domain.gateway.IRestaurantGateway

interface IGetCuisinesUseCase {
    suspend fun getCuisines(): List<Cuisine>
}

class GetCuisinesUseCase(private val cuisineGateway: IRestaurantGateway) : IGetCuisinesUseCase {
    override suspend fun getCuisines(): List<Cuisine> {
        return cuisineGateway.getCuisines()
    }
}