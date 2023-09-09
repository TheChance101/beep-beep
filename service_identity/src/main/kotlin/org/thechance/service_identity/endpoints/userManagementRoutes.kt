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
import org.thechance.service_identity.domain.util.Role

import org.thechance.service_identity.endpoints.model.UsersManagementDto
import org.thechance.service_identity.endpoints.util.extractInt
import org.thechance.service_identity.endpoints.util.toIntListOrNull

fun Route.userManagementRoutes() {

    val userManagement: IUserManagementUseCase by inject()

    route("/dashboard/user") {

        get {
            val searchTerm = call.parameters["name"] ?: ""
            val page = call.parameters.extractInt("page") ?: 1
            val limit = call.parameters.extractInt("limit") ?: 10
            val users = userManagement.getUsers(page, limit, searchTerm).toDto()
            val total = userManagement.getNumberOfUsers()
            call.respond(HttpStatusCode.OK, UsersManagementDto(users, total))
        }

        get("/search"){
            val searchTerm = call.parameters["query"] ?: ""
            val params = call.receiveParameters()
            val permission = params["permission"]?.toIntListOrNull() ?: listOf(Role.END_USER)
            val users = userManagement.searchUsers(searchTerm, permission )
            call.respond(HttpStatusCode.OK, users.toDto())
        }

        get("/last-register") {
            val limit = call.parameters.extractInt("limit") ?: 4
            val result = userManagement.getLastRegisterUser(limit).toDto()
            call.respond(HttpStatusCode.OK, result)
        }

        put("/{userId}/permission") {
            val userId = call.parameters["userId"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val permission = call.receive<List<Int>>()
            val result = userManagement.updateUserPermission(userId, permission)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

    }
}