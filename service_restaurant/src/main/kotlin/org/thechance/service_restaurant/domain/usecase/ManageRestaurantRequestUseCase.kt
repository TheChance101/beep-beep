package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.RestaurantPermissionRequest
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_EMAIL
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.nullIfFalse

interface IManageRestaurantRequestUseCase {
    suspend fun createRestaurantRequest(
        restaurantName: String,
        ownerEmail: String,
        cause: String
    ): RestaurantPermissionRequest

    suspend fun getRestaurantRequests(): List<RestaurantPermissionRequest>
}

class ManageRestaurantRequestUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val validations: IValidation
) : IManageRestaurantRequestUseCase {
    override suspend fun createRestaurantRequest(
        restaurantName: String,
        ownerEmail: String,
        cause: String
    ): RestaurantPermissionRequest {
        validations.isValidName(restaurantName).nullIfFalse() ?: throw MultiErrorException(listOf(INVALID_NAME))
        validations.isValidEmail(ownerEmail).nullIfFalse() ?: throw MultiErrorException(listOf(INVALID_EMAIL))
        return restaurantGateway.createRestaurantPermissionRequest(restaurantName, ownerEmail, cause)
    }

    override suspend fun getRestaurantRequests(): List<RestaurantPermissionRequest> {
        return restaurantGateway.getRestaurantPermissionRequests()
    }
}



