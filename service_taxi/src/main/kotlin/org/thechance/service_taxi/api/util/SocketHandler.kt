package org.thechance.service_taxi.api.util

import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOn
import org.thechance.service_taxi.api.dto.trip.TripDto
import org.thechance.service_taxi.api.dto.trip.WebSocketTrip
import java.util.concurrent.ConcurrentHashMap

class SocketHandler {

    val trips: ConcurrentHashMap<String, WebSocketTrip> = ConcurrentHashMap()
    val broadcastChannel = MutableStateFlow(TripDto())

    suspend fun collectTrips(key: String) {
        val session = trips[key]?.session
        val isATaxi = trips[key]?.isATaxiTrip

        try {
            broadcastChannel
                .drop(1)
                .flowOn(Dispatchers.IO)
                .collectLatest { tripDto ->
                    if (tripDto.isATaxiTrip == isATaxi) {
                        session?.sendSerialized(tripDto)
                    }
                }
        } catch (e: Exception) {
            session?.send(e.message.toString())
            session?.close()
        } finally {
            trips.remove(key)
        }
    }
}
