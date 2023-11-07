package org.thechance.api_gateway.endpoints.utils

import io.ktor.server.websocket.*
import org.koin.core.annotation.Single
import java.util.LinkedHashSet
import java.util.concurrent.ConcurrentHashMap


class Connection(val session: DefaultWebSocketServerSession)

@Single
class ChatSocketHandler {
    val connections: ConcurrentHashMap<String, LinkedHashSet<Connection>> = ConcurrentHashMap()
}