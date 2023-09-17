package org.thechance.api_gateway.endpoints

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.flow.collectLatest
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.LocationDto
import org.thechance.api_gateway.data.service.LocationService

fun Route.locationRoute() {
    val locationService: LocationService by inject()

    route("location") {

        // approve trip and connect to socket to send location to the user
        webSocket("/send/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().toString()
            val location = receiveDeserialized<LocationDto>()
            while (true) {
                locationService.sendLocation(location, tripId)
            }
        }

        webSocket("/receive/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().toString()
            locationService.receiveLocation(tripId).collectLatest {
                sendSerialized(it)
            }
        }

    }
}