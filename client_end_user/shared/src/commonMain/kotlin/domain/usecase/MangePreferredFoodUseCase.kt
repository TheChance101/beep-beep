package domain.usecase

import domain.gateway.local.ILocalConfigurationGateway
import io.realm.kotlin.types.RealmList

interface IMangePreferredFoodUseCase {
    suspend fun savePreferredFood(food: List<String>)
    suspend fun getPreferredFood(): List<String>?
}

class MangePreferredFoodUseCase(private val localGateway: ILocalConfigurationGateway) :
    IMangePreferredFoodUseCase {
    override suspend fun savePreferredFood(food: List<String>) {
       return localGateway.savePreferredFood(food)
    }

    override suspend fun getPreferredFood(): List<String>? {
        return localGateway.getPreferredFood()
    }
}