package org.thechance.service_identity.endpoints

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.data.mappers.toDto
import org.thechance.service_identity.domain.entity.MissingParameterException
import org.thechance.service_identity.domain.usecases.user_management.UserManagementUseCase
import org.thechance.service_identity.endpoints.validation.INVALID_REQUEST_PARAMETER

fun Route.userManagementRoutes() {

    val userManagement: UserManagementUseCase by inject()

    route("/dashboard/user") {

        get {
            val fullName = call.parameters["full_name"] ?: ""
            val username = call.parameters["username"] ?: ""
            val users = userManagement.getUsers(fullName, username)
            call.respond(users.toDto())
        }

        get("/{id}/permission") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val permissions = userManagement.getUserPermissions(id).toDto()
            call.respond(permissions)
        }

        post("/{id}/permission") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val permissionId = call.receiveParameters()["permission_id"]?.toInt()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = userManagement.addPermissionToUser(id, permissionId)
            call.respond(result)
        }

        delete("/{id}/permission") {
            val id = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val permissionId = call.parameters["permission_id"]?.toInt()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = userManagement.removePermissionFromUser(id, permissionId)
            call.respond(result)
        }

    }
}