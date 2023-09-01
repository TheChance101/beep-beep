package org.thechance.service_identity.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.endpoints.model.mapper.toDto
import org.thechance.service_identity.domain.util.MissingParameterException
import org.thechance.service_identity.domain.usecases.IUserManagementUseCase
import org.thechance.service_identity.domain.util.INVALID_REQUEST_PARAMETER
import org.thechance.service_identity.endpoints.model.UsersManagementDto
import org.thechance.service_identity.endpoints.util.extractInt

fun Route.userManagementRoutes() {

    val userManagement: IUserManagementUseCase by inject()

    route("/dashboard/user") {

        get {
            val searchTerm = call.parameters["full_name"] ?: ""
            val page = call.parameters.extractInt("page") ?: 1
            val limit = call.parameters.extractInt("limit") ?: 10
            val users = userManagement.getUsers(page, limit, searchTerm).toDto()
            val total = userManagement.getNumberOfUsers()
            call.respond(HttpStatusCode.OK, UsersManagementDto(users, total))
        }

        post("/{userId}/permission") {
            val userId = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val permission = call.receiveParameters()["permission"]?.toInt()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = userManagement.addPermissionToUser(userId, permission)
            call.respond(HttpStatusCode.Created, result.toDto())
        }

        put("/{userId}/permission") {
            val userId = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val permission = call.parameters["permission"]?.toInt()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = userManagement.removePermissionFromUser(userId, permission)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

    }
}