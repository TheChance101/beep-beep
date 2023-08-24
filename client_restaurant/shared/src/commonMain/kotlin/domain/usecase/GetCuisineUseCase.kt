package domain.usecase

import domain.entity.Cuisine
import domain.gateway.IRemoteGateWay

interface IGetCuisineUseCase {
    suspend fun getRestaurantCuisines(id: String): List<Cuisine>

    suspend fun getCuisines(): List<Cuisine>

}

class GetCuisineUseCase(private val remoteGateWay: IRemoteGateWay) : IGetCuisineUseCase {
    override suspend fun getRestaurantCuisines(id: String): List<Cuisine> {
        return remoteGateWay.getCuisine(id)
    }

    override suspend fun getCuisines(): List<Cuisine> {
        return remoteGateWay.getCuisines()
    }
}
