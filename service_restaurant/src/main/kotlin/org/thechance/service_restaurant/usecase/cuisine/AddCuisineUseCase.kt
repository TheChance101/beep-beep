package org.thechance.service_restaurant.usecase.cuisine

import org.thechance.service_restaurant.entity.Cuisine

interface AddCuisineUseCase {

    suspend operator fun invoke(cuisine: Cuisine) : Boolean

}