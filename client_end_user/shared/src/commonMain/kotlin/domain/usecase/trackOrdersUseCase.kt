package domain.usecase

import domain.entity.DeliveryRide
import domain.entity.FoodOrder
import domain.entity.TaxiRide
import domain.entity.Trip
import domain.gateway.ITransactionsGateway
import kotlinx.coroutines.flow.Flow

interface ITrackOrdersUseCase {
    suspend fun getActiveTaxiTrips(): List<Trip>
    suspend fun getActiveDeliveryTrips(): List<DeliveryRide>
    suspend fun getActiveFoodOrders(): List<FoodOrder>
    suspend fun trackTaxiRide(tripId: String): Flow<TaxiRide>
    suspend fun trackDeliveryRide(tripId: String): Flow<DeliveryRide>
    suspend fun trackFoodOrderInRestaurant(orderId: String): Flow<FoodOrder>
}

class TrackOrdersUseCase(
    private val remoteGateway: ITransactionsGateway,
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
}
