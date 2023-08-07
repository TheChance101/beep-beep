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
import org.thechance.service_identity.domain.usecases.permission.PermissionManagementUseCase
import org.thechance.service_identity.endpoints.model.PermissionDto
import org.thechance.service_identity.endpoints.validation.INVALID_REQUEST_PARAMETER

fun Route.permissionRoutes() {
    val permissionManagementUseCase: PermissionManagementUseCase by inject()
    route("/permissions") {

        post {
            val permission = call.receive<PermissionDto>()
            val success = permissionManagementUseCase.createPermission(permission.toEntity())
            call.respond(HttpStatusCode.OK, success)

        }

        delete("/{permissionId}") {
            val permissionId = call.parameters["permissionId"]?.toIntOrNull() ?: throw MissingParameterException(
                INVALID_REQUEST_PARAMETER
            )
            val success = permissionManagementUseCase.deletePermission(permissionId)
            call.respond(HttpStatusCode.OK, success)

        }

        put("/{permissionId}") {
            val permissionId =
                call.parameters["permissionId"]?.toIntOrNull() ?: throw MissingParameterException(
                    INVALID_REQUEST_PARAMETER
                )
            val permission = call.receive<PermissionDto>()
            val success = permissionManagementUseCase.updatePermission(
                permissionId,
                permission.toEntity()
            )
            call.respond(HttpStatusCode.OK, success)
        }

        get("/{permissionId}") {
            val permissionId = call.parameters["permissionId"]?.toIntOrNull()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val permission =
                permissionManagementUseCase.getPermission(permissionId).toDto()
            call.respond(HttpStatusCode.OK, permission)

        }
        get("/{permissionId}") {
            val permissionId =
                call.parameters["permissionId"]?.toIntOrNull() ?: throw MissingParameterException(
                    INVALID_REQUEST_PARAMETER
                )
            val permissions = permissionManagementUseCase.getListOfPermission(permissionId)
            call.respond(HttpStatusCode.OK, permissions.map { it.toDto() })
        }
    }
}
