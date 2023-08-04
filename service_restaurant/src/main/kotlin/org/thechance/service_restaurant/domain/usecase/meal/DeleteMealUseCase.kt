package org.thechance.service_restaurant.domain.usecase.meal

interface DeleteMealUseCase {

    suspend operator fun invoke(id: String) : Boolean

}