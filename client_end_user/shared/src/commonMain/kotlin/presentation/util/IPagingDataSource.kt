package presentation.util

import app.cash.paging.PagingData
import domain.entity.FoodOrder
import domain.entity.Meal
import domain.entity.NotificationHistory
import domain.entity.Restaurant
import domain.entity.Trip
import kotlinx.coroutines.flow.Flow

interface IPagingDataSource {

    fun getMealsInCuisine(cuisineId: String): Flow<PagingData<Meal>>
    suspend fun getRestaurants(): Flow<PagingData<Restaurant>>
    suspend fun getNotificationHistory(): Flow<PagingData<NotificationHistory>>
    suspend fun getOrdersHistory(): Flow<PagingData<FoodOrder>>
    suspend fun getTripsHistory(): Flow<PagingData<Trip>>

}