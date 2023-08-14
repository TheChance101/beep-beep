package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.usecase.validation.IRestaurantValidationUseCase
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ONE_OR_MORE_IDS
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND

interface IManageRestaurantDetailsUseCase {

    suspend fun getRestaurant(restaurantId: String): Restaurant
    suspend fun updateRestaurant(restaurant: Restaurant): Restaurant
    suspend fun deleteCategoriesInRestaurant(
        restaurantId: String, categoryIds: List<String>
    ): Boolean

}

class ManageRestaurantDetailsUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val optionsGateway: IRestaurantOptionsGateway,
    private val basicValidation: IValidation,
    private val restaurantValidation: IRestaurantValidationUseCase
) : IManageRestaurantDetailsUseCase {
    override suspend fun getRestaurant(restaurantId: String): Restaurant {
        if (!basicValidation.isValidId(restaurantId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        return restaurantGateway.getRestaurant(restaurantId) ?: throw MultiErrorException(
            listOf(
                NOT_FOUND
            )
        )
    }

    override suspend fun updateRestaurant(restaurant: Restaurant): Restaurant {
        restaurantValidation.validateUpdateRestaurantDetails(restaurant)
        val existRestaurant =
            restaurantGateway.getRestaurant(restaurant.id) ?: throw MultiErrorException(
                listOf(
                    NOT_FOUND
                )
            )
        restaurantValidation.validateRestaurantOwnership(existRestaurant, restaurant.ownerId)
        return restaurantGateway.updateRestaurant(restaurant)
    }

    override suspend fun deleteCategoriesInRestaurant(
        restaurantId: String,
        categoryIds: List<String>
    ): Boolean {
        basicValidation.checkIsValidIds(restaurantId, categoryIds)
        val validationErrors = mutableListOf<Int>()
        restaurantGateway.getRestaurant(restaurantId) ?: validationErrors.add(NOT_FOUND)
        if (!optionsGateway.areCategoriesExisting(categoryIds)) {
            validationErrors.add(
                INVALID_ONE_OR_MORE_IDS
            )
        }
        return if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        } else {
            restaurantGateway.deleteCategoriesInRestaurant(restaurantId, categoryIds)
        }
    }

}