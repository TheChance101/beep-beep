package org.thechance.service_restaurant.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.*
import org.thechance.service_restaurant.domain.gateway.CategoryGateway
import org.thechance.service_restaurant.domain.gateway.CuisineGateway
import org.thechance.service_restaurant.domain.gateway.MealGateway
import org.thechance.service_restaurant.domain.gateway.RestaurantGateway
import org.thechance.service_restaurant.utils.*
import org.thechance.service_restaurant.utils.NOT_FOUND

@Single
class AdministratorUseCaseImp(
    private val restaurantGateway: RestaurantGateway,
    private val categoryGateway: CategoryGateway,
    private val cuisineGateway: CuisineGateway,
    private val mealGateway: MealGateway
) : AdministratorUseCase {

    override suspend fun getAllMeals(page: Int, limit: Int): List<Meal> {
        return mealGateway.getMeals(page, limit)
    }

    //region cuisine
    override suspend fun addCuisine(cuisine: Cuisine): Boolean {
        if (!isValidName(cuisine.name)) {
            throw InvalidParameterException(INVALID_NAME)
        }
        return cuisineGateway.addCuisine(cuisine)
    }

    override suspend fun deleteCuisine(id: String): Boolean {
        checkIfCuisineIsExist(id)
        return cuisineGateway.deleteCuisine(id)
    }

    override suspend fun updateCuisine(cuisine: Cuisine): Boolean {
        checkIfCuisineIsExist(cuisine.id)
        if (!isValidName(cuisine.name)) {
            throw InvalidParameterException(INVALID_NAME)
        }
        return cuisineGateway.updateCuisine(cuisine)
    }

    private suspend fun checkIfCuisineIsExist(cuisineId: String) {
        if (cuisineGateway.getCuisineById(cuisineId) == null) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }

    //endregion

    //region restaurant

    override suspend fun createRestaurant(restaurant: Restaurant): Boolean {
        validationRestaurant(restaurant)
        return restaurantGateway.addRestaurant(restaurant)
    }

    override suspend fun deleteRestaurant(restaurantId: String): Boolean {
        checkIfRestaurantIsExist(restaurantId )
        return restaurantGateway.deleteRestaurant(restaurantId)
    }
    //endregion

    //region category
    override suspend fun createCategory(category: Category): Boolean {
        if (!isValidName(category.name)) {
            throw InvalidParameterException(INVALID_NAME)
        }
        return categoryGateway.addCategory(category)

    }

    override suspend fun updateCategory(category: Category): Boolean {
        validationCategory(category)
        checkIfCategoryIsExist(category.id)
        return categoryGateway.updateCategory(category)
    }

    override suspend fun deleteCategory(categoryId: String): Boolean {
        checkIfCategoryIsExist(categoryId)
        return categoryGateway.deleteCategory(categoryId)
    }

    override suspend fun addRestaurantsToCategory(categoryId: String, restaurantIds: List<String>): Boolean {
        checkIsValidIds(categoryId, restaurantIds)
        checkIfCategoryIsExist(categoryId)
        checkIfRestaurantsIsExist(restaurantIds)
        return categoryGateway.addRestaurantsToCategory(categoryId, restaurantIds)
    }


    override suspend fun deleteRestaurantsInCategory(categoryId: String, restaurantIds: List<String>): Boolean {
        checkIsValidIds(categoryId, restaurantIds)
        checkIfCategoryIsExist(categoryId)
        checkIfRestaurantsIsExist(restaurantIds)
        return categoryGateway.deleteRestaurantsInCategory(categoryId, restaurantIds)
    }

    private suspend fun checkIfCategoryIsExist(categoryId: String) {
        if (!isValidId(categoryId)) {
            throw InvalidParameterException(INVALID_ID)
        }
        if (categoryGateway.getCategory(categoryId) == null) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }

    private suspend fun checkIfRestaurantIsExist(restaurantId: String) {
        if (!isValidId(restaurantId)) {
            throw InvalidParameterException(INVALID_ID)
        }
        if (restaurantGateway.getRestaurant(restaurantId) == null) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }


    private suspend fun checkIfRestaurantsIsExist(restaurantIds: List<String>) {
        if (!restaurantGateway.getRestaurantIds().containsAll(restaurantIds)) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }

//endregion

}
