package org.thechance.service_restaurant.usecase.meal

import org.thechance.service_restaurant.entity.Meal

interface GetMealByIdUseCase {
    suspend operator fun invoke(id: String): Meal

}