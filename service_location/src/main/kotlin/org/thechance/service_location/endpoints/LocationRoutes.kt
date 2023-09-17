package org.thechance.service_location.endpoints


import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.service_location.data.SocketHandler
import org.thechance.service_location.data.model.LocationDto
import org.thechance.service_location.data.model.Trip
import org.thechance.service_location.util.*

fun Route.locationRoutes() {

    val socketHandler: SocketHandler by inject()

    route("/location") {

        webSocket("/sender/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim() ?: MissingParameterException(INVALID_REQUEST_PARAMETER)
            val location = receiveDeserialized<LocationDto>()
            try {
                socketHandler.trip[tripId]?.location?.emit(location) ?: throw InvalidLocationException(INVALID_LOCATION)
            } catch (e: ConnectionErrorException) {
                send(e.message.toString())
                this.close(CloseReason(CloseReason.Codes.TRY_AGAIN_LATER, e.toString()))
            }
        }

        webSocket("/receiver/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim() ?: MissingParameterException(INVALID_REQUEST_PARAMETER)
            try {
                socketHandler.trip[tripId.toString()] = Trip(this)
                socketHandler.broadcastLocation(tripId.toString())
            } catch (e: ConnectionErrorException) {
                send(e.message.toString())
                this.close(CloseReason(CloseReason.Codes.TRY_AGAIN_LATER, e.toString()))
            }

        }
    }
}