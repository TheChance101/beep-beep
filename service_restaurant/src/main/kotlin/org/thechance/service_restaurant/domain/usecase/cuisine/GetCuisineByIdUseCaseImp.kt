package org.thechance.service_restaurant.domain.usecase.cuisine

import io.ktor.server.plugins.*
import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.gateway.CuisineGateway

@Single
class GetCuisineByIdUseCaseImp(private val gateway: CuisineGateway) : GetCuisineByIdUseCase {
    override suspend fun invoke(id: String): Cuisine = gateway.getCuisineById(id) ?: throw NotFoundException()
}