package org.thechance.service_restaurant.domain.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.gateway.CuisineGateway

@Single
class DeleteCuisineUseCaseImp(private val gateway: CuisineGateway) : DeleteCuisineUseCase {
    override suspend fun invoke(id: String) = gateway.deleteCuisine(id)

}