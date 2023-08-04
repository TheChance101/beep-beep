package org.thechance.service_restaurant.domain.usecase.meal

import org.thechance.service_restaurant.domain.entity.Meal

interface GetMealByIdUseCase {
    suspend operator fun invoke(id: String): Meal

}