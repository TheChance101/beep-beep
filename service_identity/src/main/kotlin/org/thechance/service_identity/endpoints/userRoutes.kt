package org.thechance.service_identity.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.data.collection.CreateUserRequest
import org.thechance.service_identity.data.collection.UpdateUserRequest
import org.thechance.service_identity.data.mappers.toDto
import org.thechance.service_identity.data.mappers.toEntity
import org.thechance.service_identity.domain.entity.MissingParameterException
import org.thechance.service_identity.domain.usecases.useraccount.IManageUserAccountUseCase
import org.thechance.service_identity.endpoints.validation.INVALID_REQUEST_PARAMETER

fun Route.userRoutes() {


    val manageUserAccount: IManageUserAccountUseCase by inject()

    route("/users") {

        get("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val user = manageUserAccount.getUser(id).toDto()
            call.respond(HttpStatusCode.OK, user)
        }

        post {
            val user = call.receive<CreateUserRequest>()
            val result = manageUserAccount.createUser(user.toEntity())
            call.respond(HttpStatusCode.Created, result)
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val userDto = call.receive<UpdateUserRequest>()
            val result = manageUserAccount.updateUser(id, userDto.toEntity())
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = manageUserAccount.deleteUser(id)
            call.respond(HttpStatusCode.OK, result)
        }

    }
}