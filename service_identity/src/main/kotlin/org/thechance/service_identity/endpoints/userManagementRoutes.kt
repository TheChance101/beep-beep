package org.thechance.service_identity.endpoints

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.data.mappers.toDto
import org.thechance.service_identity.domain.usecases.user_management.UserManagementUseCase
import org.thechance.service_identity.endpoints.validation.INVALID_PERMISSION_ID_ERROR_CODE

fun Route.userManagementRoutes() {

    val userManagement: UserManagementUseCase by inject()

    route("/dashboard/user") {

        get("/{id}") {
            val id = call.parameters["id"] ?: ""
            userManagement.getUserById(id)
        }

        get {
            val fullName = call.parameters["full_name"] ?: ""
            val username = call.parameters["username"] ?: ""
            val users = userManagement.getUsersList(fullName, username)
            call.respond(users.toDto())
        }

        get("/{id}/permission") {
            val id = call.parameters["id"] ?: ""
            val permissions = userManagement.getUserPermissions(id).toDto()
            call.respond(permissions)
        }

        post("/{id}/permission") {
            val id = call.parameters["id"] ?: ""
            val permissionId = call.parameters["permission_id"]?.toInt()
                ?: throw BadRequestException(INVALID_PERMISSION_ID_ERROR_CODE)
            val result = userManagement.addPermissionToUser(id, permissionId)
            call.respond(result)
        }

        delete("/{id}/permission") {
            val id = call.parameters["id"] ?: ""
            val permissionId = call.parameters["permission_id"]?.toInt()
                ?: throw BadRequestException(INVALID_PERMISSION_ID_ERROR_CODE)
            val result = userManagement.removePermissionFromUser(id, permissionId)
            call.respond(result)
        }

    }
}