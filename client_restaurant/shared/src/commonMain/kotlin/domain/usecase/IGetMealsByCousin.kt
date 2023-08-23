package domain.usecase

import domain.entity.Meal
import domain.gateway.IRemoteGateWay

interface IGetMealsByCousin {
    suspend operator fun invoke(id:String): List<Meal>
}

class GetMealsByCousin(private val remoteGateWay: IRemoteGateWay) :IGetMealsByCousin{
    override suspend fun invoke(id: String): List<Meal> {
        return remoteGateWay.getMealsByCousinId(id)
    }

}