package org.thechance.api_gateway.endpoints

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.LocationDto
import org.thechance.api_gateway.data.service.LocationService
import org.thechance.api_gateway.endpoints.utils.WebSocketServerHandler


fun Route.locationRoute() {
    val locationService: LocationService by inject()
    val webSocketServerHandler: WebSocketServerHandler by inject()

    route("location") {

        // approve trip and connect to socket to send location to the user
        webSocket("/send/{tripId}}") {
            val tripId = call.parameters["tripId"]?.trim() ?:"65032fabdf39861b3bde1130"
            while (true) {
                runCatching {
                    val location = receiveDeserialized<LocationDto>()
                    locationService.sendLocation(location, tripId)
                }.onFailure { message ->
                    close(CloseReason(2003.toShort(), message.toString()))
                }
            }
        }

        // create trip and connect to socket to receive location of approved driver
        webSocket("/receive/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim()?:"65032fabdf39861b3bde1130"
            val locations = locationService.receiveLocation(tripId)
            webSocketServerHandler.sessions[tripId] = this
            webSocketServerHandler.tryToCollectFormWebSocket(locations, this)
        }

    }
}