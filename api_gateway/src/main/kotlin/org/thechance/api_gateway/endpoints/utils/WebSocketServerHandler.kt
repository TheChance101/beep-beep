package org.thechance.api_gateway.endpoints.utils

import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.utils.LocalizedMessageException
import java.util.concurrent.ConcurrentHashMap

@Single
class WebSocketServerHandler {

    val sessions: ConcurrentHashMap<String, DefaultWebSocketServerSession> = ConcurrentHashMap()

    suspend inline fun <reified T> tryToCollectFormWebSocket(values: Flow<T>, session: DefaultWebSocketServerSession) {
        try {
            values.flowOn(Dispatchers.IO).collect { value -> session.sendSerialized(value) }
        } catch (e: LocalizedMessageException) {
            session.send(e.localizedMessage)
            session.close()
        }
    }
}