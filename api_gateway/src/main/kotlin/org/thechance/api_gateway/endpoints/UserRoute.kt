package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.receiveParameters
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.service.IdentityService
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.endpoints.utils.toIntListOrNull
import org.thechance.api_gateway.util.Role


fun Route.userRoutes() {
    val identityService: IdentityService by inject()

    authenticateWithRole(Role.DASHBOARD_ADMIN) {



        get("/users") {
            val language = extractLocalizationHeader()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val searchTerm = call.parameters["searchTerm"] ?: ""
            val result = identityService.getUsers(
                page = page,
                limit = limit,
                searchTerm = searchTerm,
                language
            )
            respondWithResult(HttpStatusCode.OK, result)
        }

        route("/user") {

            delete("/{id}") {
                val id = call.parameters["id"] ?: ""
                val language = extractLocalizationHeader()
                val result = identityService.deleteUser(userId = id, language)
                respondWithResult(HttpStatusCode.OK, result)
            }

            put("/{userId}/permission") {
                val userId = call.parameters["userId"]?.trim().toString()
                val params = call.receiveParameters()
                val permission = params["permission"]?.toIntListOrNull() ?: listOf(Role.END_USER)
                val result = identityService.updateUserPermission(userId, permission)
                respondWithResult(HttpStatusCode.Created, result)
            }
            get("/last-register") {
                val limit = call.parameters["limit"]?.toInt() ?: 4
                val result = identityService.getLastRegisteredUsers(limit)
                respondWithResult(HttpStatusCode.OK, result)
            }

            get("/search"){
                val searchTerm = call.parameters["query"] ?: ""
                val params = call.receiveParameters()
                val permission = params["permission"]?.toIntListOrNull() ?: listOf(Role.END_USER)
                val users = identityService.searchUsers(searchTerm, permission)
                respondWithResult(HttpStatusCode.OK, users)
            }
        }
    }
}

