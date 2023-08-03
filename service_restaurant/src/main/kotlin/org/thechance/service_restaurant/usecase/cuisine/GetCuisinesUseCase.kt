package org.thechance.service_restaurant.usecase.cuisine

import org.thechance.service_restaurant.entity.Cuisine

interface GetCuisinesUseCase {

    suspend operator fun invoke(page : Int , limit : Int): List<Cuisine>

}