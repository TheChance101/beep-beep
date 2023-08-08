package org.thechance.service_identity.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.data.mappers.toDto
import org.thechance.service_identity.data.mappers.toEntity
import org.thechance.service_identity.domain.entity.MissingParameterException
import org.thechance.service_identity.domain.usecases.useraccount.UserAccountUseCase
import org.thechance.service_identity.endpoints.model.request.CreateUserRequest
import org.thechance.service_identity.endpoints.model.request.UpdateUserRequest
import org.thechance.service_identity.endpoints.validation.INVALID_REQUEST_PARAMETER

fun Route.userRoutes() {


    val userAccountUseCase: UserAccountUseCase by inject()

    route("/users") {

        get {
            val users = userAccountUseCase.getUsers()
            call.respond(HttpStatusCode.OK, users.map { it.toDto() })
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val user = userAccountUseCase.getUser(id).toDto()
            call.respond(HttpStatusCode.OK, user)
        }

        post {
            val user = call.receive<CreateUserRequest>()
            val result = userAccountUseCase.createUser(user.toEntity())
            call.respond(HttpStatusCode.Created, result)
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val userDto = call.receive<UpdateUserRequest>()
            val result = userAccountUseCase.updateUser(id, userDto.toEntity())
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = userAccountUseCase.deleteUser(id)
            call.respond(HttpStatusCode.OK, result)
        }

    }
}