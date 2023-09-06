package org.thechance.service_location.util

import org.thechance.service_location.data.model.WebSocketLocation
import java.util.concurrent.ConcurrentHashMap


class SocketHandler {
    val location:ConcurrentHashMap<String,WebSocketLocation> = ConcurrentHashMap()

}