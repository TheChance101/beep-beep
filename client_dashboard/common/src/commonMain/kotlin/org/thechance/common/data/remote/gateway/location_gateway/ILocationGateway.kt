package org.thechance.common.data.remote.gateway.location_gateway

import org.thechance.common.domain.entity.Location

interface ILocationGateway {
    suspend fun getCurrentLocation(): Location
}