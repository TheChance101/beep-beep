package org.thechance.service_location.data

import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOn
import org.thechance.service_location.data.model.Trip
import org.thechance.service_location.util.ConnectionErrorException
import java.util.concurrent.ConcurrentHashMap

class SocketHandler {

    val trip: ConcurrentHashMap<String, Trip> = ConcurrentHashMap()

    suspend fun broadcastLocation(tripId: String) {

        val session = trip[tripId]?.session
        val currentLocation = trip[tripId]?.location

        try {
            currentLocation
                ?.drop(1)
                ?.flowOn(Dispatchers.IO)
                ?.collect { location ->
                    session?.sendSerialized(location)
                }
        } catch (e: ConnectionErrorException) {
            session?.close(CloseReason(CloseReason.Codes.TRY_AGAIN_LATER, e.toString()))
        } finally {
            trip.remove(tripId)
        }
    }

}