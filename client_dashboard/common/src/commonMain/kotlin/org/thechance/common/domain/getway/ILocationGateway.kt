package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.LocationInfo

interface ILocationGateway {
    suspend fun getCurrentLocation(): LocationInfo

    suspend fun getIpAddress(): String
}