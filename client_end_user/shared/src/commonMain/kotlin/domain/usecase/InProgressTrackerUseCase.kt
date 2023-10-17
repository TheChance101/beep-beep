package domain.usecase

import domain.entity.FoodOrder
import domain.entity.Trip
import domain.gateway.ITransactionsGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface IInProgressTrackerUseCase {
    suspend fun getActiveTaxiTrips(): Flow<List<Trip>>
    suspend fun getActiveDeliveryTrips(): Flow<List<Trip>>
    suspend fun getActiveFoodOrders(): Flow<List<FoodOrder>>
}

class InProgressTrackerUseCase(
    private val remoteGateway: ITransactionsGateway
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
}
