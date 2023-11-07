package domain.gateway.local

import domain.entity.Location
import kotlinx.coroutines.flow.Flow

interface ILocationGateway {
    suspend fun startTracking()
    suspend fun getCurrentLocation(): Flow<Location>
    suspend fun stopTracking()
}