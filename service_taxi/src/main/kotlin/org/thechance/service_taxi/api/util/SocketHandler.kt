package org.thechance.service_taxi.api.util

import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.thechance.service_taxi.api.dto.trip.TripDto
import org.thechance.service_taxi.api.dto.trip.WebSocketTrip
import java.util.concurrent.ConcurrentHashMap

@OptIn(DelicateCoroutinesApi::class)
class SocketHandler {

    val trips: ConcurrentHashMap<String, WebSocketTrip> = ConcurrentHashMap()
    private val broadcastChannel = MutableStateFlow(TripDto())

    init {
        GlobalScope.launch {
            broadcastChannel
                .drop(1)
                .flowOn(Dispatchers.IO)
                .collect { tripDto ->
                    trips.values
                        .filter { it.isATaxiTrip == tripDto.isATaxiTrip }
                        .forEach { it.session.sendSerialized(tripDto) }
                }
        }
    }

    suspend fun collectTrips(key: String) {
        val session = trips[key]?.session
        val trip = trips[key]?.trips

        try {
            trip?.collect { tripDto -> broadcastChannel.emit(tripDto) }
        } catch (e: Exception) {
            session?.send(e.message.toString())
            session?.close()
        } finally {
            trips.remove(key)
        }
    }
}
