package domain.gateway

import domain.entity.Location
import kotlinx.coroutines.flow.Flow

interface ILocationGateway {
    suspend fun trackCurrentLocation(): Flow<Location>
}