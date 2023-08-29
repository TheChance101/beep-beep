package org.thechance.common.data.remote.gateway

import org.thechance.common.domain.entity.Location
import org.thechance.common.domain.getway.ILocationGateway

class LocationGateway: ILocationGateway {
    override suspend fun getCurrentLocation(): Location {
        TODO("Not yet implemented")
    }
}