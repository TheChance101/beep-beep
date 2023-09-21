package data.remote.fakegateway

import domain.dataSource.IBpLocationDataSource
import domain.gateway.ILocationGateway

class LocationFakeGateway(
    private val bpLocation: IBpLocationDataSource,
) : ILocationGateway {
    override suspend fun trackCurrentLocation() = bpLocation.getCurrentLocation()
}