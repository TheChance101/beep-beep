package domain.gateway

import domain.entity.Cart
import domain.entity.DeliveryRide
import domain.entity.FoodOrder
import domain.entity.Location
import domain.entity.PaginationItems
import domain.entity.TaxiRide
import domain.entity.Trip
import kotlinx.coroutines.flow.Flow

interface ITransactionsGateway {
    suspend fun getTripHistory(page: Int, limit: Int): PaginationItems<Trip>
    suspend fun getOrderHistoryGateway(page: Int, limit: Int): PaginationItems<FoodOrder>
    suspend fun getCart(): Cart
    suspend fun addMealToCart(mealId: String, restaurantId: String, quantity: Int): Cart

    suspend fun orderNow(): Boolean
    suspend fun updateCart(cart: Cart)
    suspend fun getActiveTaxiTrips(): List<Trip>
    suspend fun getActiveDeliveryTrips(): List<DeliveryRide>
    suspend fun getActiveOrders(): List<FoodOrder>
    suspend fun getTripByOrderId(orderId: String): Trip
    suspend fun getTripByTripId(tripId: String): Trip
    suspend fun getOrderByOrderId(orderId: String): FoodOrder
    suspend fun trackTaxiRide(tripId: String): Flow<TaxiRide>
    suspend fun trackDeliveryRide(tripId: String): Flow<DeliveryRide>
    suspend fun trackFoodOrderInRestaurant(orderId: String): Flow<FoodOrder>
    suspend fun trackDriverLocation(tripId: String): Flow<Location>
}
