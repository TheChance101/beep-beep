package domain.usecase

import domain.entity.Cuisine
import domain.entity.Meal
import domain.gateway.IRemoteGateWay

interface IMangeCousinUseCase {
    suspend  fun getCuisine(id:String): List<Cuisine>
    suspend  fun getMealsByCuisineId(id:String): List<Meal>
}

class MangeCousinUseCase(private val remoteGateWay: IRemoteGateWay) : IMangeCousinUseCase{
    override suspend fun getCuisine(id:String): List<Cuisine> {
        return remoteGateWay.getCuisine(id)
    }

    override suspend fun getMealsByCuisineId(id: String): List<Meal> {
        return remoteGateWay.getMealsByCuisineId(id)
    }

}