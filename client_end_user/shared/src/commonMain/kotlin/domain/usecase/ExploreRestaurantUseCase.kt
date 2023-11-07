package domain.usecase

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import data.gateway.remote.pagesource.MealsPagingSource
import domain.entity.Cuisine
import domain.entity.Meal
import domain.entity.Restaurant
import domain.gateway.IRestaurantGateway
import kotlinx.coroutines.flow.Flow

interface IExploreRestaurantUseCase {
    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun getMealById(mealId: String): Meal
    suspend fun getCuisines(): List<Cuisine>
    suspend fun getMealsInCuisine(cuisineId: String): Flow<PagingData<Meal>>
    suspend fun getCuisinesWithMealsInRestaurant(restaurantId: String): List<Cuisine>
}

class ExploreRestaurantUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val mealDataSource: MealsPagingSource,
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

    override suspend fun getMealsInCuisine(cuisineId: String): Flow<PagingData<Meal>> {
        mealDataSource.initCuisine(cuisineId)
        return Pager(config = PagingConfig(pageSize = 1),
            pagingSourceFactory = { mealDataSource }
        ).flow
    }

    override suspend fun getCuisinesWithMealsInRestaurant(restaurantId: String): List<Cuisine> {
        return restaurantGateway.getCuisinesWithMealsInRestaurant(restaurantId)
    }
}
