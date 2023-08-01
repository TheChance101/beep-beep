package org.thechance.service_restaurant.api.usecases

import org.thechance.service_restaurant.entity.Meal
interface GetMealsUseCase {

    suspend operator fun invoke(page : Int , limit : Int): List<Meal>

}