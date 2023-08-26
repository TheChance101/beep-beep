package domain.usecase

import data.remote.gateway.FakeRemoteGateWay
import domain.entity.Cuisine
import domain.entity.Meal
import domain.gateway.IRemoteGateWay

interface IMangeCuisineUseCase {
    suspend fun getCuisineByResturantId(id: String): List<Cuisine>
    suspend fun getCuisines(): List<Cuisine>
    suspend fun getMealsByCuisineId(id: String): List<Meal>
}

class MangeCuisineUseCase(private val remoteGateWay: FakeRemoteGateWay) : IMangeCuisineUseCase {
    override suspend fun getCuisineByResturantId(id: String): List<Cuisine> {
        return remoteGateWay.getCuisineByRestaurantId(id)
    }

    override suspend fun getMealsByCuisineId(id: String): List<Meal> {
        return remoteGateWay.getMealsByCuisineId(id)
    }

    override suspend fun getCuisines(): List<Cuisine> {
        return remoteGateWay.getCuisines()
    }
}
