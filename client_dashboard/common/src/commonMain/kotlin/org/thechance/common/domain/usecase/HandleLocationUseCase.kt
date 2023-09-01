package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Location
import org.thechance.common.domain.getway.IRemoteGateway

interface IHandleLocationUseCase {
    suspend fun getCurrentLocation(): String
}


class HandleLocationUseCase(private val remoteGateway: IRemoteGateway) : IHandleLocationUseCase {
    override suspend fun getCurrentLocation(): String {
        return remoteGateway.getCurrentLocation()
    }
}