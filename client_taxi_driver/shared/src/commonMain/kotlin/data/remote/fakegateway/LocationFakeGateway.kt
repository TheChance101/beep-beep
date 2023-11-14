package data.remote.fakegateway

import domain.entity.Location
import domain.gateway.ILocationGateway
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow


class LocationFakeGateway : ILocationGateway {
    override suspend fun trackCurrentLocation() = flow {
        var lattiude = 30.044420
        while (true) {
            lattiude += 0.00004
            emit(
                Location(
                    latitude = lattiude,
                    longitude = 31.235712,
                )
            )
            delay(2000)
        }
    }
}