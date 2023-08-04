package org.thechance.service_restaurant.domain.usecase.cuisine

import org.thechance.service_restaurant.domain.entity.Cuisine

interface UpdateCuisineUseCase {

    suspend operator fun invoke(cuisine: Cuisine): Boolean

}