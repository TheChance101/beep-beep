package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.Location

interface ILocationGateway {
    suspend fun getCurrentLocation(): Location
}