package data.gateway.remote.pagesource

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import domain.entity.FoodOrder
import domain.entity.Meal
import domain.entity.NotificationHistory
import domain.entity.Restaurant
import domain.entity.Trip
import kotlinx.coroutines.flow.Flow
import presentation.util.IPagingDataSource

class PagingDataSource(
    private val mealDataSource: MealsPagingSource,
    private val restaurantDataSource: RestaurantsPagingSource,
    private val foodOrderPagingSource: FoodOrderPagingSource,
    private val notificationPagingSource: NotificationPagingSource,
    private val taxiOrderPagingSource: TaxiOrderPagingSource,
) : IPagingDataSource {
    override fun getMealsInCuisine(cuisineId: String): Flow<PagingData<Meal>> {
        mealDataSource.initCuisine(cuisineId)
        return Pager(config = PagingConfig(pageSize = 5), pagingSourceFactory = { mealDataSource }
        ).flow
    }

    override suspend fun getRestaurants(): Flow<PagingData<Restaurant>> {
        return Pager(
            config = PagingConfig(pageSize = 10), pagingSourceFactory = { restaurantDataSource },
        ).flow
    }

    override suspend fun getNotificationHistory(): Flow<androidx.paging.PagingData<NotificationHistory>> {
        return Pager(config = androidx.paging.PagingConfig(
            pageSize = 10,
            enablePlaceholders = true
        ),
            pagingSourceFactory = { notificationPagingSource }
        ).flow
    }

    override suspend fun getOrdersHistory(): Flow<androidx.paging.PagingData<FoodOrder>> {
        return androidx.paging.Pager(config = androidx.paging.PagingConfig(
            pageSize = 10,
            enablePlaceholders = true
        ),
            pagingSourceFactory = { foodOrderPagingSource }
        ).flow
    }

    override suspend fun getTripsHistory(): Flow<androidx.paging.PagingData<Trip>> {
        return androidx.paging.Pager(config = androidx.paging.PagingConfig(
            pageSize = 10,
            enablePlaceholders = true
        ),
            pagingSourceFactory = { taxiOrderPagingSource }
        ).flow
    }

}