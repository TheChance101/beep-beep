package org.thechance.service_restaurant.api.usecases

import org.thechance.service_restaurant.entity.Meal

interface GetMealByIdUseCase {
    suspend operator fun invoke(id: String): Meal

}