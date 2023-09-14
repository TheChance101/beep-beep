package org.thechance.common.domain.usecase

import org.thechance.common.domain.getway.ILocationGateway
import org.thechance.common.domain.getway.IRemoteGateway

interface IManageLocationUseCase {
    suspend fun getCurrentLocation(): Pair<String, String>
}


class ManageLocationUseCase(private val remoteGateway: IRemoteGateway) : IManageLocationUseCase {
    override suspend fun getCurrentLocation(): Pair<String, String> {
        val latLng = remoteGateway.getCurrentLocation().split(",")
        return Pair(latLng[0], latLng[1])
    }
}