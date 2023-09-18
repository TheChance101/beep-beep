package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.LocationInfo
import org.thechance.common.domain.getway.ILocationGateway

interface IManageLocationUseCase {
    suspend fun getCurrentLocation(): LocationInfo
}


class ManageLocationUseCase(private val locationGateway: ILocationGateway) : IManageLocationUseCase {
    override suspend fun getCurrentLocation(): LocationInfo {
        return locationGateway.getCurrentLocation()
    }
}