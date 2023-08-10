package org.thechance.service_identity.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.data.mappers.toDto
import org.thechance.service_identity.domain.entity.MissingParameterException
import org.thechance.service_identity.domain.usecases.IUserManagementUseCase
import org.thechance.service_identity.domain.util.INVALID_REQUEST_PARAMETER
import org.thechance.service_identity.endpoints.util.extractInt

fun Route.userManagementRoutes() {

    val userManagement: IUserManagementUseCase by inject()

    route("/dashboard/user") {

        get {
            val page = call.parameters.extractInt("page") ?: 1
            val limit = call.parameters.extractInt("limit") ?: 10
            val users = userManagement.getUsers(page, limit).toDto()
            call.respond(HttpStatusCode.OK, users)
        }

        get("/search/{search_term}") {
            val searchTerm = call.parameters["search_term"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val users = userManagement.searchUsers(searchTerm).toDto()
            call.respond(HttpStatusCode.OK, users)
        }

        get("/{id}/permission") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val permissions = userManagement.getUserPermissions(id).toDto()
            call.respond(HttpStatusCode.OK, permissions)
        }

        post("/{id}/permission") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val permissionId = call.receiveParameters()["permission_id"]?.toInt()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = userManagement.addPermissionToUser(id, permissionId)
            call.respond(HttpStatusCode.Created, result)
        }

        delete("/{id}/permission") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val permissionId = call.parameters["permission_id"]?.toInt()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = userManagement.removePermissionFromUser(id, permissionId)
            call.respond(HttpStatusCode.OK, result)
        }

    }
}