package org.thechance.common.data.remote.gateway.location_gateway

import org.thechance.common.domain.entity.Location

class LocationGateway:ILocationGateway {
    override suspend fun getCurrentLocation(): Location {
        TODO("Not yet implemented")
    }
}