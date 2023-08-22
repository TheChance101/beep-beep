package domain.usecase

import domain.entity.Meal
import domain.gateway.IRemoteGateWay
import presentation.base.ServerSideException

interface IManageMealUseCase {
    suspend fun addMeal()

    suspend fun getMeal(mealId: String): Meal

    suspend fun updateMeal(meal: Meal)
}

class ManageMealUseCase(private val remoteGateWay: IRemoteGateWay) : IManageMealUseCase {
    override suspend fun addMeal() {
        TODO("Not yet implemented")
    }

    override suspend fun getMeal(mealId: String): Meal {
        return remoteGateWay.getMealById(mealId) ?: throw ServerSideException()
    }

    override suspend fun updateMeal(meal: Meal) {
        TODO("Not yet implemented")
    }


}