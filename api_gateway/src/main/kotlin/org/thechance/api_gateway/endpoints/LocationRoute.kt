package org.thechance.api_gateway.endpoints

import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.taxi.TripDto
import org.thechance.api_gateway.data.service.LocationService
import org.thechance.api_gateway.data.service.TaxiService
import org.thechance.api_gateway.endpoints.utils.WebSocketServerHandler
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeaderFromWebSocket

fun Route.locationRoute() {
    val locationService: LocationService by inject()
    val webSocketServerHandler: WebSocketServerHandler by inject()
    val taxiService: TaxiService by inject()

    route("location") {

        webSocket("/send}") {

            val language = extractLocalizationHeaderFromWebSocket()
            val trip = call.receive<TripDto>()
            val createdTrip = taxiService.createTrip(trip, language)

            val tripId = createdTrip.id

            if (tripId != null) {
                val location = locationService.sendLocation(tripId)
                webSocketServerHandler.sessions[tripId] = this
                locationService.sendLocation(tripId)

                webSocketServerHandler.sessions[tripId]?.let {
                    webSocketServerHandler.tryToCollectFormWebSocket(location, this)
                }
            }

        }
        webSocket("/receive/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            val location = locationService.receiveLocation(tripId)
            webSocketServerHandler.sessions[tripId] = this
            webSocketServerHandler.sessions[tripId]?.let {
                webSocketServerHandler.tryToCollectFormWebSocket(location, this)
            }
        }

    }

}