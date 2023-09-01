package org.thechance.common.domain.usecase

import org.thechance.common.domain.getway.IRemoteGateway

interface IHandleLocationUseCase {
    suspend fun getCurrentLocation(): Pair<String, String>
}


class HandleLocationUseCase(private val remoteGateway: IRemoteGateway) : IHandleLocationUseCase {
    override suspend fun getCurrentLocation(): Pair<String, String> {
        val latLng = remoteGateway.getCurrentLocation().split(",")
        return Pair(latLng[0], latLng[1])
    }
}