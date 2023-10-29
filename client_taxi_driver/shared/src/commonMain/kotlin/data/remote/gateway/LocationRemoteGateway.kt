package data.remote.gateway

import domain.dataSource.IBpLocationDataSource
import domain.gateway.ILocationGateway
import kotlinx.coroutines.flow.distinctUntilChanged

class LocationRemoteGateway(
    private val bpLocation: IBpLocationDataSource,
) : ILocationGateway {
    override suspend fun trackCurrentLocation() =
        bpLocation.getCurrentLocation().distinctUntilChanged()
}