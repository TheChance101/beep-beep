package domain.usecase

import domain.entity.Cuisine
import domain.gateway.ICuisineRemoteGateway

interface IGetCuisinesUseCase {
    suspend fun getCuisines(): List<Cuisine>
}

class GetCuisinesUseCase(private val cuisineGateway: ICuisineRemoteGateway) : IGetCuisinesUseCase {
    override suspend fun getCuisines(): List<Cuisine> {
        return cuisineGateway.getCuisines()
    }
}