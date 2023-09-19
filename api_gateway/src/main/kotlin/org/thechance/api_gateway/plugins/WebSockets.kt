package org.thechance.api_gateway.plugins

import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.websocket.*
import kotlinx.serialization.json.Json
import java.time.Duration

fun Application.configureSockets() {
    install(WebSockets) {
        contentConverter =  KotlinxWebsocketSerializationConverter(Json)
        pingPeriod = Duration.ofSeconds(120000)
        timeout = Duration.ofSeconds(120000)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
}
