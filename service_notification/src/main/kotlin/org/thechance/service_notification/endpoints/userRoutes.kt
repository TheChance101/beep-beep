package org.thechance.service_notification.endpoints

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import org.thechance.service_notification.data.mappers.toDto
import org.thechance.service_notification.data.mappers.toEntity
import org.thechance.service_notification.domain.usecases.IUserManagementUseCase
import org.thechance.service_notification.endpoints.model.UserDto

fun Route.userRoutes() {
    val useCase: IUserManagementUseCase by inject()

    route("/users") {
        post {
            val user = call.receive<UserDto>()
            val result = useCase.createUser(user.toEntity())
            call.respond(HttpStatusCode.Created, "User was created: $result")
        }
        post("/{userId}/token") {
            val userId = call.parameters["userId"] ?: throw BadRequestException("User id is required")
            val token = call.receive<String>()
            val result = useCase.addTokenToUser(userId, token)
            call.respond("Token was added: $result")
        }
        get {
            val users = useCase.getUsers().toDto()
            call.respond(HttpStatusCode.OK, users)
        }
        get("/{userId}") {
            val id = call.parameters["userId"] ?: throw BadRequestException("User id is required")
            val user = useCase.getUser(id).toDto()
            call.respond(HttpStatusCode.OK, user)
        }
    }
}