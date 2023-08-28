package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import java.util.*


fun Route.dashboardRoutes() {
    val restaurantGateway: IRestaurantGateway by inject()


    route("/dashboard") {

        authenticate("auth-jwt") {
            get("/restaurant-permission-request") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java) ?: emptyList()
                val (language, countryCode) = extractLocalizationHeader()
                val result = restaurantGateway.getAllRequestPermission(permissions, Locale(language, countryCode))

                respondWithResult(HttpStatusCode.OK, result)
            }
        }
    }
}