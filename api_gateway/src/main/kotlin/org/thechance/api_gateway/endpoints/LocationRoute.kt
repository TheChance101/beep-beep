package org.thechance.api_gateway.endpoints

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.LocationDto
import org.thechance.api_gateway.data.service.LocationService
import org.thechance.api_gateway.endpoints.utils.WebSocketServerHandler


fun Route.locationRoute() {
    val locationService: LocationService by inject()
    val webSocketServerHandler: WebSocketServerHandler by inject()

    route("location") {

        webSocket("/send/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            while (true) {
                val location = receiveDeserialized<LocationDto>()
                locationService.sendLocation(location, tripId)
            }
        }

        webSocket("/receive/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            val locations = locationService.receiveLocation(tripId)
            webSocketServerHandler.sessions[tripId] = this
            webSocketServerHandler.sessions[tripId]?.let {
                webSocketServerHandler.tryToCollectFormWebSocket(locations, it)
            }
        }

    }
}