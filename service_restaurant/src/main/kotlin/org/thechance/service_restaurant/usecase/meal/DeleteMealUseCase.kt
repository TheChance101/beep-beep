package org.thechance.service_restaurant.usecase.meal

interface DeleteMealUseCase {
    suspend operator fun invoke(id: String) : Boolean

}