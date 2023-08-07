package org.thechance.service_notification.endpoints

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_notification.data.collection.UserCollection
import org.thechance.service_notification.domain.gateway.IDatabaseGateway


fun Route.testRoutes() {
    val databaseGateway: IDatabaseGateway by inject()
    get("/test") {
        call.respond("welcome to notification service")
    }

    post("/user") {
        val user = call.receive<UserCollection>()
        databaseGateway.createUser(user)
        call.respond("Created Successfully")
    }
}
