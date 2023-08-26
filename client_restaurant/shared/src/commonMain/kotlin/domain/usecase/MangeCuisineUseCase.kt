package domain.usecase

import domain.entity.Cuisine
import domain.entity.Meal
import domain.gateway.IFakeRemoteGateWay
import domain.gateway.IRemoteCuisineGateway

interface IMangeCuisineUseCase {
    suspend fun getCuisine(id: String): List<Cuisine>
    suspend fun getCuisines(): List<Cuisine>
    suspend fun getMealsByCuisineId(id: String): List<Meal>
}

class MangeCuisineUseCase(private val remoteCuisineGateway: IFakeRemoteGateWay) :
    IMangeCuisineUseCase {
    override suspend fun getCuisine(id: String): List<Cuisine> {
        return remoteCuisineGateway.getCuisine(id)
    }

    override suspend fun getMealsByCuisineId(id: String): List<Meal> {
        return remoteCuisineGateway.getMealsByCuisineId(id)
    }

    override suspend fun getCuisines(): List<Cuisine> {
        return remoteCuisineGateway.getCuisines()
    }
}
