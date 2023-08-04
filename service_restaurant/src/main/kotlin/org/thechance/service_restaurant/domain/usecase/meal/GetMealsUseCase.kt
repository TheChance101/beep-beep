package org.thechance.service_restaurant.domain.usecase.meal

import org.thechance.service_restaurant.domain.entity.Meal
interface GetMealsUseCase {

    suspend operator fun invoke(page : Int , limit : Int): List<Meal>

}