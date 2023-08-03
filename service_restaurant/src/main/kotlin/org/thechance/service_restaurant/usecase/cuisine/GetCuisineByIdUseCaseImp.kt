package org.thechance.service_restaurant.usecase.cuisine

import io.ktor.server.plugins.*
import org.koin.core.annotation.Single
import org.thechance.service_restaurant.entity.Cuisine
import org.thechance.service_restaurant.data.gateway.CuisineGateway

@Single
class GetCuisineByIdUseCaseImp(private val gateway: CuisineGateway) : GetCuisineByIdUseCase {
    override suspend fun invoke(id: String): Cuisine = gateway.getCuisineById(id) ?: throw NotFoundException()
}