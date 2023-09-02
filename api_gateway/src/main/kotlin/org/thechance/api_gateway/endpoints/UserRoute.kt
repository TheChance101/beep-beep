package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.service.IdentityService
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
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
        }
    }
}

