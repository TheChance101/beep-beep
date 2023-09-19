package org.thechance.service_location.endpoints


import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.service_location.data.SocketHandler
import org.thechance.service_location.data.model.LocationDto
import org.thechance.service_location.data.model.WebSocketLocation
import org.thechance.service_location.util.INVALID_LOCATION

fun Route.locationRoutes() {

    val socketHandler: SocketHandler by inject()

    route("/location") {

        webSocket("/sender/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            runCatching {
                val location = receiveDeserialized<LocationDto>()
                socketHandler.location[tripId]?.locations?.emit(location)
                println("ssss websocket ${location.latitude} , ${location.longitude}")
            }.onFailure { message ->
                close(CloseReason(INVALID_LOCATION.toShort(), message.toString()))
            }
        }

        webSocket("/receiver/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            socketHandler.location[tripId] = WebSocketLocation(this)
            socketHandler.broadcastLocation(tripId)
        }
    }
}