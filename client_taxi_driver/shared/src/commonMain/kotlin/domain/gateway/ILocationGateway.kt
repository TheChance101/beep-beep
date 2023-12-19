package domain.gateway

import domain.entity.Location
import kotlinx.coroutines.flow.Flow

interface ILocationGateway {
    suspend fun startTracking()
    suspend fun trackCurrentLocation(): Flow<Location>
    suspend fun stopTracking()
}