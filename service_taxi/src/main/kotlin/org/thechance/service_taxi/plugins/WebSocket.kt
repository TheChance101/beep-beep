package org.thechance.service_taxi.plugins

import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.websocket.*
import kotlinx.serialization.json.Json
import java.time.Duration

fun Application.configureWebSocket() {
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
        pingPeriod = Duration.ofSeconds(1500)
        timeout = Duration.ofSeconds(1500)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
}