package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Location
import org.thechance.common.domain.getway.IRemoteGateway

interface IHandleLocationUseCase {
    suspend fun getCurrentLocation(): Location
}


class HandleLocationUseCase(private val remoteGateway: IRemoteGateway) : IHandleLocationUseCase {
    override suspend fun getCurrentLocation(): Location {
        return remoteGateway.getCurrentLocation()
    }
}