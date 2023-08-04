package org.thechance.service_restaurant.domain.usecase.cuisine

import org.thechance.service_restaurant.domain.entity.Cuisine

interface ManageCuisineUseCase {
    suspend fun getCuisines(page : Int , limit : Int): List<Cuisine>
}