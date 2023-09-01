package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.endpoints.gateway.IIdentityGateway
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Role
import java.util.*


fun Route.userRoutes() {
    val identityGateway: IIdentityGateway by inject()

    authenticateWithRole(Role.DASHBOARD_ADMIN) {

        get("/users") {
            val (language, countryCode) = extractLocalizationHeader()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = identityGateway.getUsers(page = page, limit = limit, locale = Locale(language, countryCode))
            respondWithResult(HttpStatusCode.OK, result)
        }

        route("/user") {

            delete("/{id}") {
                val id = call.parameters["id"] ?: ""
                val (language, countryCode) = extractLocalizationHeader()
                val result = identityGateway.deleteUser(userId = id, locale = Locale(language, countryCode))
                respondWithResult(HttpStatusCode.OK, result)
            }
        }
    }
}

