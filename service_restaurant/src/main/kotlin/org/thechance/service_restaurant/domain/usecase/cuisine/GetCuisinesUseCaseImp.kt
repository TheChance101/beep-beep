package org.thechance.service_restaurant.domain.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.gateway.CuisineGateway

@Single
class GetCuisinesUseCaseImp(private val gateway: CuisineGateway) : GetCuisinesUseCase {
    override suspend fun invoke(page : Int , limit : Int) = gateway.getCuisines(page, limit)

}