package org.thechance.api_gateway.data.service

import io.ktor.client.*
import io.ktor.util.*
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.LocationDto
import org.thechance.api_gateway.data.utils.tryToExecuteFromWebSocket
import org.thechance.api_gateway.data.utils.tryToSendWebSocketData
import org.thechance.api_gateway.util.APIs

@Single
class LocationService(
    private val client: HttpClient,
    private val attributes: Attributes
) {

    suspend fun sendLocation(location: LocationDto, tripId: String) {
        client.tryToSendWebSocketData(
            data = location,
            api = APIs.LOCATION_API,
            attributes = attributes,
            path = "/location/sender/$tripId"
        )
    }

    suspend fun receiveLocation(tripId: String): Flow<LocationDto> {
        return client.tryToExecuteFromWebSocket<LocationDto>(
            api = APIs.LOCATION_API,
            attributes = attributes,
            path = "/location/receiver/$tripId",
        )
    }
}
