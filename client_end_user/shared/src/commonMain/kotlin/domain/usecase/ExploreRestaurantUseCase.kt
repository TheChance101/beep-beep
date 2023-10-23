package domain.usecase

import androidx.paging.cachedIn
import androidx.paging.map
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.seiko.imageloader.model.DataSource
import data.gateway.remote.pagesource.BasePagingSource
import data.gateway.remote.pagesource.MealsPagingSource
import domain.entity.Cuisine
import domain.entity.Meal
import domain.entity.Restaurant
import domain.gateway.IRestaurantGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

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
) : IExploreRestaurantUseCase, KoinComponent {


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
        return Pager(config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { mealDataSource }
        ).flow
//        return  mealDataSource.getAllWithParameters(cuisineId,restaurantGateway){params ->
//            MealsPagingSource(restaurantGateway,params[1] as String)
//        }
    }

    override suspend fun getCuisinesWithMealsInRestaurant(restaurantId: String): List<Cuisine> {
        return restaurantGateway.getCuisinesWithMealsInRestaurant(restaurantId)
    }
}
