package org.thechance.service_notification.endpoints

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import org.thechance.service_notification.data.mappers.toEntity
import org.thechance.service_notification.domain.usecases.CreateUserUseCase
import org.thechance.service_notification.endpoints.model.UserDto

fun Route.userRoutes() {
    val createUser: CreateUserUseCase by inject()

    route("/users") {
        post {
            val user = call.receive<UserDto>()
            createUser(user.toEntity())
            call.respond(HttpStatusCode.Created, "User created successfully")
        }

    }
}