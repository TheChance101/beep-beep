package org.thechance.service_restaurant.domain.usecase.cuisine

interface DeleteCuisineUseCase {

    suspend operator fun invoke(id: String): Boolean

}