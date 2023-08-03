package org.thechance.service_identity.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_identity.api.model.PermissionDto
import org.thechance.service_identity.data.mappers.toDto
import org.thechance.service_identity.data.mappers.toEntity
import org.thechance.service_identity.domain.usecases.permission.PermissionUseCasesContainer

fun Route.permissionRoutes() {
    val permissionUseCasesContainer: PermissionUseCasesContainer by inject()
    route("/permissions") {

        post {
            val permission = call.receive<PermissionDto>()
            val success = permissionUseCasesContainer.addPermission(permission.toEntity())
            if (success) {
                call.respond(HttpStatusCode.Created)
            } else {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        delete("/{permissionId}") {
            val permissionId = call.parameters["permissionId"] ?: return@delete call.respond(
                HttpStatusCode.BadRequest
            )
            val success = permissionUseCasesContainer.deletePermission(permissionId)
            if (success) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        put("/{permissionId}") {
            val permissionId = call.parameters["permissionId"] ?: return@put call.respond(
                HttpStatusCode.BadRequest
            )
            val permission = call.receive<PermissionDto>()
            if (permission.id != permissionId) {
                return@put call.respond(HttpStatusCode.BadRequest)
            }
            val success = permissionUseCasesContainer.updatePermission(
                permissionId,
                permission.toEntity()
            )
            if (success) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        get("/{permissionId}") {
            val permissionId = call.parameters["permissionId"]
                ?: throw IllegalArgumentException("Invalid permission ID")
            val permission =
                permissionUseCasesContainer.getPermission(permissionId).toDto()
            call.respond(HttpStatusCode.OK, permission)

        }
    }
}
