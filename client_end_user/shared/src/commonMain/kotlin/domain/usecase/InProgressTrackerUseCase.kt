package domain.usecase

import domain.entity.DeliveryRide
import domain.entity.FoodOrder
import domain.entity.TaxiRide
import domain.entity.Trip
import domain.gateway.ITransactionsGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import presentation.home.toTaxiRideUiState

interface IInProgressTrackerUseCase {
    suspend fun getActiveTaxiTrips(): Flow<List<Trip>>
    suspend fun getActiveDeliveryTrips(): Flow<List<Trip>>
    suspend fun getActiveFoodOrders(): Flow<List<FoodOrder>>
    suspend fun trackTaxiRide(tripId: String): Flow<TaxiRide>
    suspend fun trackDeliveryRide(tripId: String): Flow<DeliveryRide>
    suspend fun trackFoodOrderInRestaurant(orderId: String): Flow<FoodOrder>

}

class InProgressTrackerUseCase(
    private val remoteGateway: ITransactionsGateway,
) : IInProgressTrackerUseCase {
    override suspend fun getActiveTaxiTrips(): Flow<List<Trip>> {
        val trips = remoteGateway.getActiveTrips()
        val activeTaxiRides = trips.filter { it.isATaxiTrip }
        return flowOf(activeTaxiRides)
    }

    override suspend fun getActiveDeliveryTrips(): Flow<List<Trip>> {
        val trips = remoteGateway.getActiveTrips()
        val activeDeliveryOrders = trips.filterNot { it.isATaxiTrip }
        return flowOf(activeDeliveryOrders)
    }

    override suspend fun getActiveFoodOrders(): Flow<List<FoodOrder>> {
        val orders = remoteGateway.getActiveOrders()
        return flowOf(orders)
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
