package org.thechance.service_restaurant.usecase

import io.ktor.server.plugins.*
import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.GetMealByIdUseCase
import org.thechance.service_restaurant.entity.Meal
import org.thechance.service_restaurant.usecase.gateway.MealGateway

@Single
class GetMealByIdUseCaseImp(private val mealGateway: MealGateway) : GetMealByIdUseCase {
    override suspend fun invoke(id: String): Meal = mealGateway.getMealById(id) ?: throw NotFoundException()

}