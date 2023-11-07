package org.thechance.service_chat.plugins

import io.ktor.server.application.*
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.Koin
import org.thechance.service_chat.di.ChatModule
import org.thechance.service_chat.di.kmongoModule

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(
            ChatModule().module,
            kmongoModule
        )
    }
}