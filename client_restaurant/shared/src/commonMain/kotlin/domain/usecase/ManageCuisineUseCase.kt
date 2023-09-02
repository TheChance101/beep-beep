package domain.usecase

import domain.entity.Cuisine
import domain.gateway.remote.ICuisineRemoteGateway

interface IManageCuisineUseCase {
    suspend fun getCuisines(): List<Cuisine>
}

class ManageCuisineUseCase(private val cuisineRemoteGateway: ICuisineRemoteGateway) :
    IManageCuisineUseCase {

    override suspend fun getCuisines(): List<Cuisine> {
        return cuisineRemoteGateway.getCuisines()
    }
}
