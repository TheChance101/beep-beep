package org.thechance.service_identity.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.api.model.UserDto
import org.thechance.service_identity.domain.usecases.user.UserUseCaseContainer

fun Route.userRoutes() {

    val userUseCaseContainer: UserUseCaseContainer by inject()

    route("/users") {

        get {
            val users = userUseCaseContainer.getUsersUseCase.invoke()
            call.respond(HttpStatusCode.OK, users.map { it.toUserDto() })
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: ""
            val user = userUseCaseContainer.getUserByIdUseCase.invoke(id).toUserDto()
            call.respond(HttpStatusCode.OK, user)
        }

        post {
            val user = call.receive<UserDto>()
            val result = userUseCaseContainer.createUserUseCase.invoke(user.toUser())
            call.respond(HttpStatusCode.Created, result)
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: ""
            val userDto = call.receive<UserDto>()
            val result = userUseCaseContainer.updateUserUseCase.invoke(id, userDto.toUser())
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: ""
            val result = userUseCaseContainer.deleteUserUseCase.invoke(id)
            call.respond(HttpStatusCode.OK, result)

        }
    }



}