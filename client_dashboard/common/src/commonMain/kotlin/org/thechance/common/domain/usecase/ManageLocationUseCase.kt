package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Location
import org.thechance.common.domain.getway.IRemoteGateway

interface IManageLocationUseCase {
    suspend fun getCurrentLocation(): Location
}


class ManageLocationUseCase(private val remoteGateway: IRemoteGateway) : IManageLocationUseCase {
    override suspend fun getCurrentLocation(): Location {
        return remoteGateway.getCurrentLocation()
    }
}