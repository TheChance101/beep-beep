package domain.usecase

import domain.gateway.local.ILocalConfigurationGateway

interface IMangePreferredFoodUseCase {
    suspend fun savePreferredFood(food: String)
    suspend fun getPreferredFood(): String
}

class MangePreferredFoodUseCase(private val localGateway: ILocalConfigurationGateway) :
    IMangePreferredFoodUseCase {
    override suspend fun savePreferredFood(food: String) {
       return localGateway.savePreferredFood(food)
    }

    override suspend fun getPreferredFood(): String {
        return localGateway.getPreferredFood()
    }
}