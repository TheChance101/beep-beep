package org.thechance.service_restaurant.usecase.cuisine

interface DeleteCuisineUseCase {

    suspend operator fun invoke(id: String): Boolean

}