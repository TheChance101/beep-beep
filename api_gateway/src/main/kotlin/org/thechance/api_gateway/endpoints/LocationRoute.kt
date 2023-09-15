package org.thechance.api_gateway.endpoints

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.LocationDto
import org.thechance.api_gateway.data.service.LocationService
import org.thechance.api_gateway.endpoints.utils.WebSocketServerHandler
import java.util.concurrent.ConcurrentHashMap

data class Trip(val receive: DefaultWebSocketServerSession? = null, val send: DefaultWebSocketServerSession? = null)

lateinit var trip: Trip
fun Route.locationRoute() {
    val locationService: LocationService by inject()
    val webSocketServerHandler: WebSocketServerHandler by inject()

    val sessions: ConcurrentHashMap<String, Trip> = ConcurrentHashMap()


    route("location") {

        // approve trip and connect to socket to send location to the user
        webSocket("/send/{tripId}}") {
            val tripId = call.parameters["tripId"]?.trim() ?: "6502804a4a1d280f71ebfba1"
            trip = if (::trip.isInitialized) {
                trip.copy(send = this@webSocket)
            } else {
                Trip(send = this@webSocket)
            }
            sessions[tripId] = trip

            println("Sockettttt : $sessions")
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
            val tripId = call.parameters["tripId"]?.trim() ?: "6502804a4a1d280f71ebfba1"
            trip = if (::trip.isInitialized) {
                trip.copy(receive = this@webSocket)
            } else {
                Trip(receive = this@webSocket)
            }
            sessions[tripId] = trip
            println("Sockettttt : $sessions")
            sessions[tripId]?.receive?.let {
                webSocketServerHandler.tryToCollectFormWebSocket(locationService.receiveLocation(tripId), it)
            }
        }
    }
}