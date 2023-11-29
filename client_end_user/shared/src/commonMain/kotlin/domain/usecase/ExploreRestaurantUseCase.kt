package domain.usecase

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import data.gateway.remote.pagesource.MealsPagingSource
import data.gateway.remote.pagesource.RestaurantsPagingSource
import domain.entity.Cuisine
import domain.entity.Meal
import domain.entity.PaginationItems
import domain.entity.Restaurant
import domain.gateway.IRestaurantGateway
import domain.gateway.local.ILocalConfigurationGateway
import kotlinx.coroutines.flow.Flow

interface IExploreRestaurantUseCase {
    suspend fun getRestaurants(page:Int,limit:Int): PaginationItems<Restaurant>
    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun getMealById(mealId: String): Meal
    suspend fun getCuisines(): List<Cuisine>
    suspend fun getPreferredCuisines(): List<Cuisine>
    suspend fun getMealsInCuisine(cuisineId: String,page:Int,limit:Int): PaginationItems<Meal>
    suspend fun getCuisinesWithMealsInRestaurant(restaurantId: String): List<Cuisine>
}

class ExploreRestaurantUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val localGateway: ILocalConfigurationGateway
) : IExploreRestaurantUseCase {


    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        return restaurantGateway.getRestaurantDetails(restaurantId)
    }

    override suspend fun getMealById(mealId: String): Meal {
        return restaurantGateway.getMealById(mealId)
    }

    override suspend fun getCuisines(): List<Cuisine> {
        return restaurantGateway.getCuisines()
    }

    override suspend fun getPreferredCuisines(): List<Cuisine> {
        val preferredFood = localGateway.getPreferredFood()
        val cuisines = restaurantGateway.getCuisines()
        return cuisines.filter { preferredFood.contains(it.id) }
    }

    override suspend fun getRestaurants(page:Int,limit:Int): PaginationItems<Restaurant> {
        return restaurantGateway.getRestaurants(page = page, limit = limit)
    }

    override suspend fun getMealsInCuisine(cuisineId: String,page:Int,limit:Int): PaginationItems<Meal> {
       return restaurantGateway.getMealsInCuisine(cuisineId,page= page,limit= limit)
    }

    override suspend fun getCuisinesWithMealsInRestaurant(restaurantId: String): List<Cuisine> {
        return restaurantGateway.getCuisinesWithMealsInRestaurant(restaurantId)
    }
}
