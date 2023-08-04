package org.thechance.service_restaurant.domain.usecase.meal

import org.thechance.service_restaurant.domain.entity.Meal


interface AddMealUseCase {
    suspend operator fun invoke(meal: Meal): Boolean

}