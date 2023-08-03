package org.thechance.service_restaurant.usecase.meal

import org.thechance.service_restaurant.entity.Meal


interface AddMealUseCase {
    suspend operator fun invoke(meal: Meal): Boolean

}