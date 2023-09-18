package data.remote.fakegateway

import domain.entity.Location
import domain.gateway.ILocationGateway
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocationFakeGateway : ILocationGateway {
    override fun streamLiveLocation(): Flow<Location> {
        return flow {
            var lattiude = 30.044420
            while (true) {
                lattiude += 0.00002
                emit(
                    Location(
                        lat = lattiude.toString(),
                        lng = 31.235712.toString(),
                        name = "",
                    )
                )
                delay(1000)
            }
        }
    }
}