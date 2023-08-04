package org.thechance.service_restaurant.domain.usecase.meal

import io.ktor.server.plugins.*
import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.gateway.MealGateway

@Single
class GetMealByIdUseCaseImp(private val mealGateway: MealGateway) : GetMealByIdUseCase {
    override suspend fun invoke(id: String): Meal = mealGateway.getMealById(id) ?: throw NotFoundException()

}