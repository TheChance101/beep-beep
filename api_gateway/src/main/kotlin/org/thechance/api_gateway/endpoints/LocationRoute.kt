package org.thechance.api_gateway.endpoints

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.LocationDto
import org.thechance.api_gateway.data.service.LocationService
import org.thechance.api_gateway.endpoints.utils.WebSocketServerHandler
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeaderFromWebSocket


fun Route.locationRoute() {
    val locationService: LocationService by inject()
    val webSocketServerHandler: WebSocketServerHandler by inject()

    route("location") {

        // approve trip and connect to socket to send location to the user
        webSocket("/send/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            while (true) {
                val location = receiveDeserialized<LocationDto>()
                locationService.sendLocation(location, tripId)
            }
        }

        webSocket("/receive/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            locationService.receiveLocation(tripId).collectLatest {
                sendSerialized(it)
            }
        }

    }
}