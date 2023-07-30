package org.thechance.service_restaurant.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.repository.BeepRepository

@Single
class CreateRestaurantUseCase(private val repository: BeepRepository) {
    suspend operator fun invoke(name: String): Boolean {
       return repository.addRestaurants(name = name)
    }

}