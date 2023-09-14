package org.thechance.api_gateway.endpoints

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.service.LocationService
import org.thechance.api_gateway.data.service.TaxiService
import org.thechance.api_gateway.endpoints.utils.WebSocketServerHandler
import java.util.concurrent.ConcurrentHashMap

fun Route.locationRoute() {
    val locationService: LocationService by inject()
    val webSocketServerHandler: WebSocketServerHandler by inject()

    route("location") {

        // approve trip and connect to socket to send location to the user
        webSocket("/send/{tripId}}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()

            webSocketServerHandler.sessions[tripId] = this

            val location = locationService.sendLocation(tripId)
            webSocketServerHandler.sessions[tripId]?.let {
                webSocketServerHandler.tryToCollectFormWebSocket(location, this)
            }
        }

        // create trip and connect to socket to receive location of approved driver
        webSocket("/receive/{tripId}") {

            val tripId = call.parameters["tripId"]?.trim().orEmpty()

            webSocketServerHandler.sessions[tripId] = this
            val location = locationService.receiveLocation(tripId)

            webSocketServerHandler.sessions[tripId]?.let {
                webSocketServerHandler.tryToCollectFormWebSocket(location, this)
            }
        }

    }


//    route("location") {
//
//        webSocket("/trip/{tripId}") {
//
//            val tripId = call.parameters["tripId"]?.trim().orEmpty()
//            val action = call.request.queryParameters["action"]?.trim().orEmpty()
//
//            webSocketServerHandler.sessions[tripId] = this
//
//            when (action) {
//                "send" -> {
//                    // Send location
////                    val location = locationService.sendLocation(tripId)
//                    webSocketServerHandler.sessions[tripId]?.let {
//                        webSocketServerHandler.tryToCollectFormWebSocket(locationService.sendLocation(tripId), this)
//                    }
//                }
//
//                "receive" -> {
//                    // Receive location
////                    val location =
//                    webSocketServerHandler.sessions[tripId]?.let {
//                        webSocketServerHandler.tryToCollectFormWebSocket(locationService.receiveLocation(tripId), this)
//                    }
//                }
//
//                else -> {
//                    // Handle unsupported action
//                    close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Invalid action"))
//                }
//            }
//        }
//    }


}