package domain.gateway

import domain.entity.Location
import kotlinx.coroutines.flow.Flow

interface ILocationGateway {
    fun trackCurrentLocation(): Flow<Location>
}