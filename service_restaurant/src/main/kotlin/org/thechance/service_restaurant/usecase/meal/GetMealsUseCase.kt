package org.thechance.service_restaurant.usecase.meal

import org.thechance.service_restaurant.entity.Meal
interface GetMealsUseCase {

    suspend operator fun invoke(page : Int , limit : Int): List<Meal>

}