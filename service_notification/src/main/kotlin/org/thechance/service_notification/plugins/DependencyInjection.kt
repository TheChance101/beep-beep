package org.thechance.service_notification.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.Koin
import org.thechance.service_notification.di.NotificationModule
import org.thechance.service_notification.di.firebaseModule
import org.thechance.service_notification.di.kmongoModule

fun Application.configureDependencyInjection() {
    install(Koin) {
        slf4jLogger()
        modules(
            NotificationModule().module,
            kmongoModule,
            firebaseModule
        )
    }
}