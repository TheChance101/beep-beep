package domain.gateway

import domain.entity.Cart
import domain.entity.DeliveryRide
import domain.entity.FoodOrder
import domain.entity.TaxiRide
import domain.entity.Trip
import kotlinx.coroutines.flow.Flow

interface ITransactionsGateway {
    suspend fun getTripHistory(): List<Trip>
    suspend fun getOrderHistoryGateway(): List<FoodOrder>
    suspend fun getCart(): Cart
    suspend fun getActiveTrips(): List<Trip>
    suspend fun getActiveOrders(): List<FoodOrder>
    suspend fun trackTaxiRide(tripId: String): Flow<TaxiRide>
    suspend fun trackDeliveryRide(tripId: String): Flow<DeliveryRide>
    suspend fun trackFoodOrderInRestaurant(orderId: String): Flow<FoodOrder>
}
