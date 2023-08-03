package org.thechance.service_restaurant.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.CuisineGateway

@Single
class GetCuisinesUseCaseImp(private val gateway: CuisineGateway) : GetCuisinesUseCase {
    override suspend fun invoke(page : Int , limit : Int) = gateway.getCuisines(page, limit)

}