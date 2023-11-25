package domain.usecase

import domain.entity.Location
import domain.entity.Order
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.remote.IMapRemoteGateway
import kotlinx.coroutines.flow.Flow

interface IManageOrderUseCase {
    suspend fun getOrders(): Flow<Order>
    suspend fun broadcastLocation(location: Location, tripId: String)
    suspend fun updateTrip(tripId: String): Order
    suspend fun calculateDistance(
        startLat: Double,
        startLng: Double,
        endLat: Double,
        endLng: Double,
    ): Double

    suspend fun calculateTimeInMinutes(distanceInKilometers: Double): Double
}

class ManageOrderUseCase(
    private val remoteGateway: IMapRemoteGateway,
    private val localGateWay: ILocalConfigurationGateway,
) : IManageOrderUseCase {
    override suspend fun getOrders(): Flow<Order> {
        return remoteGateway.getOrders()
    }

    override suspend fun broadcastLocation(location: Location, tripId: String) {
        remoteGateway.sendLocation(location, tripId)
    }

    override suspend fun updateTrip(tripId: String): Order {
        val taxiId = getTaxiId()
        return remoteGateway.updateTrip(taxiId, tripId)
    }

    private suspend fun getTaxiId(): String {
        return localGateWay.getTaxiId()
    }


    override suspend fun calculateDistance(
        startLat: Double,
        startLng: Double,
        endLat: Double,
        endLng: Double,
    ): Double {
        val earthRadius = 6371.0

        val dLat = kotlin.math.sin(kotlin.math.PI * (endLat - startLat) / 180)
        val dLng = kotlin.math.sin(kotlin.math.PI * (endLng - startLng) / 180)

        val a =
            dLat * dLat + kotlin.math.cos(kotlin.math.PI * startLat / 180) * kotlin.math.cos(kotlin.math.PI * endLat / 180) * dLng * dLng

        val c = 2 * kotlin.math.atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))

        return earthRadius * c
    }

    override suspend fun calculateTimeInMinutes(distanceInKilometers: Double): Double {
        if (distanceInKilometers <= 0) {
            throw IllegalArgumentException("Distance must be positive")
        }
        val speedInKilometersPerMinute = 0.7
        return distanceInKilometers / speedInKilometersPerMinute
    }
}