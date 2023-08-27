package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.RestaurantPermissionRequest
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_EMAIL
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.nullIfTrue

interface IManageRestaurantRequestUseCase {
    suspend fun createRestaurantRequest(restaurantName: String, ownerEmail: String, cause: String)
    suspend fun getRestaurantRequests(): List<RestaurantPermissionRequest>
}

class ManageRestaurantRequestUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val validations: IValidation
) : IManageRestaurantRequestUseCase {
    override suspend fun createRestaurantRequest(restaurantName: String, ownerEmail: String, cause: String) {
        validations.isValidName(restaurantName).nullIfTrue() ?: throw MultiErrorException(listOf(INVALID_NAME))
        validations.isValidEmail(ownerEmail).nullIfTrue() ?: throw MultiErrorException(listOf(INVALID_EMAIL))
        restaurantGateway.createRestaurantPermissionRequest(restaurantName, ownerEmail, cause)
    }

    override suspend fun getRestaurantRequests() : List<RestaurantPermissionRequest> {
        return restaurantGateway.getRestaurantPermissionRequests()
    }
}



