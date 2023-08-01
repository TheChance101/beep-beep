package org.thechance.service_restaurant.usecase.gateway

import org.thechance.service_restaurant.entity.Cuisine

interface CuisineGateway {

    suspend fun addCuisine(cuisine: Cuisine): Boolean

    suspend fun deleteCuisine(id: String): Boolean

    suspend fun getCuisines(page: Int, limit: Int): List<Cuisine>

    suspend fun getCuisineById(id: String): Cuisine?

    suspend fun updateCuisine(cuisine: Cuisine): Boolean
}