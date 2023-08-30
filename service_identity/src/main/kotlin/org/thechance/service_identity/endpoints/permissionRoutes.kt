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
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.usecases.IPermissionManagementUseCase
import org.thechance.service_identity.domain.util.INVALID_REQUEST_PARAMETER
import org.thechance.service_identity.endpoints.model.PermissionDto

fun Route.permissionRoutes() {
    val permissionManagementUseCase: IPermissionManagementUseCase by inject()
    route("/permissions") {
        get {
            val permissions = permissionManagementUseCase.getListOfPermission()
            call.respond(HttpStatusCode.OK, permissions.map { it.toDto() })
        }

        get("/{permissionId}") {
            val permissionId = call.parameters["permissionId"]?.toIntOrNull()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val permission = permissionManagementUseCase.getPermission(permissionId).toDto()
            call.respond(HttpStatusCode.OK, permission)
        }

        post {
            val permissionDto = call.receive<PermissionDto>()
            val success = permissionManagementUseCase.createPermission(permissionDto.toEntity())
            call.respond(HttpStatusCode.OK, success)
        }

        put("/{permissionId}") {
            val permissionId = call.parameters["permissionId"]?.toIntOrNull()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val permission = call.receive<Permission>()
            val success = permissionManagementUseCase.updatePermission(permissionId, permission.permission)
            call.respond(HttpStatusCode.OK, success)
        }

        delete("/{permissionId}") {
            val permissionId = call.parameters["permissionId"]?.toIntOrNull()
                ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val success = permissionManagementUseCase.deletePermission(permissionId)
            call.respond(HttpStatusCode.OK, success)
        }
    }
}
