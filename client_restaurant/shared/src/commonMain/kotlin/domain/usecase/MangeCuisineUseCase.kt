package domain.usecase

import domain.entity.Cuisine
import domain.gateway.remote.ICuisineRemoteGateway

interface IMangeCuisineUseCase {
    suspend fun getCuisines(): List<Cuisine>
}

class MangeCuisineUseCase(private val cuisineRemoteGateway: ICuisineRemoteGateway) :
    IMangeCuisineUseCase {

    override suspend fun getCuisines(): List<Cuisine> {
        return cuisineRemoteGateway.getCuisines()
    }
}
