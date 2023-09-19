package data.remote.fakegateway

import domain.entity.Location
import domain.gateway.ILocationGateway
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class LocationFakeGateway : ILocationGateway {
    override fun trackCurrentLocation() = flow {
        var lattiude = 30.044420
        while (true) {
            lattiude += 0.00002
            emit(
                Location(
                    lat = lattiude,
                    lng = 31.235712,
                    addressName = "",
                )
            )
            delay(2000)
        }
    }
}