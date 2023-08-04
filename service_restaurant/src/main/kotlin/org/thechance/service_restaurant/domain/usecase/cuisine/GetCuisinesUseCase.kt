package org.thechance.service_restaurant.domain.usecase.cuisine

import org.thechance.service_restaurant.domain.entity.Cuisine

interface GetCuisinesUseCase {

    suspend operator fun invoke(page : Int , limit : Int): List<Cuisine>

}