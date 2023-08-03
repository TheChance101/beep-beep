package org.thechance.service_restaurant.usecase.cuisine

import org.thechance.service_restaurant.entity.Cuisine

interface GetCuisineByIdUseCase {

    suspend operator fun invoke(id: String): Cuisine

}