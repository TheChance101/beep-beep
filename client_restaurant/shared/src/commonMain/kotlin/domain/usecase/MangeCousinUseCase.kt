package domain.usecase

import domain.entity.Cousin
import domain.entity.Meal
import domain.gateway.IRemoteGateWay

interface IMangeCousinUseCase {
    suspend  fun getCousins(id:String): List<Cousin>
    suspend  fun getMealsByCousinId(id:String): List<Meal>
}

class MangeCousinUseCase(private val remoteGateWay: IRemoteGateWay) : IMangeCousinUseCase{
    override suspend fun getCousins(id:String): List<Cousin> {
        return remoteGateWay.getCousins(id)
    }

    override suspend fun getMealsByCousinId(id: String): List<Meal> {
        return remoteGateWay.getMealsByCousinId(id)
    }

}