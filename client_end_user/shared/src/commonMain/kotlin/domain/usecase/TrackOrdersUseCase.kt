package domain.usecase

import domain.entity.DeliveryRide
import domain.entity.FoodOrder
import domain.entity.Location
import domain.entity.TaxiRide
import domain.entity.Trip
import domain.entity.TripStatus
import domain.gateway.ITransactionsGateway
import domain.gateway.IUserGateway
import kotlinx.coroutines.flow.Flow

interface ITrackOrdersUseCase {
    suspend fun getActiveTaxiTrips(): List<Trip>
    suspend fun getActiveDeliveryTrips(): List<DeliveryRide>
    suspend fun getActiveFoodOrders(): List<FoodOrder>
    suspend fun trackTaxiRide(tripId: String): Flow<TaxiRide>
    suspend fun trackDeliveryRide(tripId: String): Flow<DeliveryRide>
    suspend fun trackFoodOrderInRestaurant(orderId: String): Flow<FoodOrder>
    suspend fun trackDriverLocation(tripId: String): Flow<Location>
    suspend fun getUserOrderLocation(): Location
    suspend fun getTripId(orderId: String): String
    suspend fun getTripStatus(tripId: String): TripStatus
    suspend fun getOrderStatus(orderId: String): FoodOrder.OrderStatusInRestaurant
}

class TrackOrdersUseCase(
    private val remoteGateway: ITransactionsGateway,
    private val userGateway: IUserGateway,
) : ITrackOrdersUseCase {
    override suspend fun getActiveTaxiTrips(): List<Trip> {
        return remoteGateway.getActiveTaxiTrips()
    }

    override suspend fun getActiveDeliveryTrips(): List<DeliveryRide> {
        return remoteGateway.getActiveDeliveryTrips()
    }

    override suspend fun getActiveFoodOrders(): List<FoodOrder> {
        return remoteGateway.getActiveOrders()
    }

    override suspend fun trackTaxiRide(tripId: String): Flow<TaxiRide> {
        return remoteGateway.trackTaxiRide(tripId)
    }

    override suspend fun trackDeliveryRide(tripId: String): Flow<DeliveryRide> {
        return remoteGateway.trackDeliveryRide(tripId)
    }

    override suspend fun trackFoodOrderInRestaurant(orderId: String): Flow<FoodOrder> {
        return remoteGateway.trackFoodOrderInRestaurant(orderId)
    }

    override suspend fun trackDriverLocation(tripId: String): Flow<Location> {
        return remoteGateway.trackDriverLocation(tripId)
    }

    override suspend fun getUserOrderLocation(): Location {
        val firstUserAddress = userGateway.getUserAddresses().first()
        return firstUserAddress.location ?: Location(30.44075, 30.966551)
    }

    override suspend fun getTripId(orderId: String): String {
        return remoteGateway.getTripByOrderId(orderId).id
    }

    override suspend fun getTripStatus(tripId: String): TripStatus {
        return remoteGateway.getTripByTripId(tripId).tripStatus
    }

    override suspend fun getOrderStatus(orderId: String): FoodOrder.OrderStatusInRestaurant {
        return remoteGateway.getOrderByOrderId(orderId).orderStatus
    }
}