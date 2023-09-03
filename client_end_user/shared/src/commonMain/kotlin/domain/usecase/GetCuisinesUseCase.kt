package domain.usecase

import domain.entity.Cuisine
import domain.gateway.IRestaurantRemoteGateway

interface IGetCuisinesUseCase {
    suspend fun getCuisines(): List<Cuisine>
}

class GetCuisinesUseCase(private val cuisineGateway: IRestaurantRemoteGateway) : IGetCuisinesUseCase {
    override suspend fun getCuisines(): List<Cuisine> {
        return cuisineGateway.getCuisines()
    }
}