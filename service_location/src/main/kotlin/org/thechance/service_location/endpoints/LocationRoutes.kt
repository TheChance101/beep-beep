package org.thechance.service_location.endpoints


import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import org.thechance.service_location.data.model.LocationDto
import org.thechance.service_location.data.model.WebSocketLocation
import org.thechance.service_location.data.SocketHandler

fun Route.locationRoutes() {

    val socketHandler: SocketHandler by inject()

    route("/location") {

        webSocket("/sender/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val location = Json.decodeFromString<LocationDto>(frame.readText())
                    socketHandler.location[tripId]?.locations?.emit(location)
                }
            }
        }

        webSocket("/receiver/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            socketHandler.location[tripId] = WebSocketLocation(this)
            socketHandler.broadcastLocation(tripId)
        }
    }
}