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
import org.thechance.service_identity.endpoints.model.UserOptionsDto
import org.thechance.service_identity.endpoints.model.mapper.toEntity
import org.thechance.service_identity.endpoints.model.BasePagingResponse
import org.thechance.service_identity.endpoints.util.extractInt

fun Route.userManagementRoutes() {

    val userManagement: IUserManagementUseCase by inject()

    route("/dashboard/user") {

        post {
            val options = call.receive<UserOptionsDto>().toEntity()
            val restaurants = userManagement.getUsers(options).toDto()
            val total = userManagement.getNumberOfUsers()
            call.respond(HttpStatusCode.OK, BasePagingResponse(restaurants, page = options.page, total))
        }

        get("/last-register") {
            val limit = call.parameters.extractInt("limit") ?: 4
            val result = userManagement.getLastRegisterUser(limit).toDto()
            call.respond(HttpStatusCode.OK, result)
        }

        put("/{userId}/permission") {
            val userId = call.parameters["userId"] ?: throw MissingParameterException(
                INVALID_REQUEST_PARAMETER
            )
            val permission: List<Int> = call.receive<List<Int>>()
            val result = userManagement.updateUserPermission(userId, permission)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

    }
}