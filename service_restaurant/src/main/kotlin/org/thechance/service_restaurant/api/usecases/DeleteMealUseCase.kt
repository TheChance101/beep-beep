package org.thechance.service_restaurant.api.usecases

interface DeleteMealUseCase {
    suspend operator fun invoke(id: String) : Boolean

}