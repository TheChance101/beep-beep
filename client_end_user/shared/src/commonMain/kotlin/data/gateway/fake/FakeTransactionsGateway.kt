package data.gateway.fake

import domain.entity.Cart
import domain.entity.DeliveryRide
import domain.entity.FoodOrder
import domain.entity.Location
import domain.entity.PaginationItems
import domain.entity.TaxiColor
import domain.entity.TaxiRide
import domain.entity.Trip
import domain.entity.TripStatus
import domain.gateway.ITransactionsGateway
import kotlinx.coroutines.flow.Flow

class FakeTransactionsGateway : ITransactionsGateway {
    override suspend fun getTripHistory(page: Int, limit: Int): PaginationItems<Trip> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrderHistoryGateway(page: Int, limit: Int): PaginationItems<FoodOrder> {
        TODO("Not yet implemented")
    }

    override suspend fun getCart(): Cart {
        TODO("Not yet implemented")
    }

    override suspend fun addMealToCart(mealId: String, restaurantId: String, quantity: Int): Cart {
        TODO("Not yet implemented")
    }

    override suspend fun orderNow(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateCart(cart: Cart) {
        TODO("Not yet implemented")
    }

    override suspend fun getActiveTaxiTrips(): List<Trip> {
        return emptyList()
    }

    override suspend fun getActiveDeliveryTrips(): List<DeliveryRide> {
        TODO("Not yet implemented")
    }

    override suspend fun getActiveOrders(): List<FoodOrder> {
        TODO("Not yet implemented")
    }

    override suspend fun getTripByOrderId(orderId: String): Trip {
        TODO("Not yet implemented")
    }

    override suspend fun getTripByTripId(tripId: String): Trip {
        TODO("Not yet implemented")
    }

    override suspend fun getOrderByOrderId(orderId: String): FoodOrder {
        TODO("Not yet implemented")
    }

    override suspend fun trackTaxiRide(tripId: String): Flow<TaxiRide> {
        TODO("Not yet implemented")
    }

    override suspend fun trackDeliveryRide(tripId: String): Flow<DeliveryRide> {
        TODO("Not yet implemented")
    }

    override suspend fun trackFoodOrderInRestaurant(orderId: String): Flow<FoodOrder> {
        TODO("Not yet implemented")
    }

    override suspend fun trackDriverLocation(tripId: String): Flow<Location> {
        TODO("Not yet implemented")
    }

}
