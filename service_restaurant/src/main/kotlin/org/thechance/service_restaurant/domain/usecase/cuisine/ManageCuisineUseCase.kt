package org.thechance.service_restaurant.domain.usecase.cuisine

import org.thechance.service_restaurant.domain.entity.Cuisine

interface ManageCuisineUseCase {

    suspend fun updateCuisine(cuisine: Cuisine): Boolean

    suspend fun getCuisines(page : Int , limit : Int): List<Cuisine>

    suspend fun deleteCuisine(id: String): Boolean

    suspend fun addCuisine(cuisine: Cuisine) : Boolean
}