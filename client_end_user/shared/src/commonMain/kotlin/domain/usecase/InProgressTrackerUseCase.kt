package domain.usecase

import domain.entity.DeliveryRide
import domain.entity.FoodOrder
import domain.entity.TaxiRide
import domain.entity.Trip
import domain.gateway.ITransactionsGateway
import kotlinx.coroutines.flow.Flow

interface IInProgressTrackerUseCase {
    suspend fun getActiveTaxiTrips(): List<Trip>
    suspend fun getActiveDeliveryTrips(): List<Trip>
    suspend fun getActiveFoodOrders(): List<FoodOrder>
    suspend fun trackTaxiRide(tripId: String): Flow<TaxiRide>
    suspend fun trackDeliveryRide(tripId: String): Flow<DeliveryRide>
    suspend fun trackFoodOrderInRestaurant(orderId: String): Flow<FoodOrder>
}

class InProgressTrackerUseCase(
    private val remoteGateway: ITransactionsGateway,
) : IInProgressTrackerUseCase {
    override suspend fun getActiveTaxiTrips(): List<Trip> {
        val trips = remoteGateway.getActiveTrips()
        return trips.filter { it.isATaxiTrip }
    }

    override suspend fun getActiveDeliveryTrips(): List<Trip> {
        val trips = remoteGateway.getActiveTrips()
        return trips.filterNot { it.isATaxiTrip }
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
}
