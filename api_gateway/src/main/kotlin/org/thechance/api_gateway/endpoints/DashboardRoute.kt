package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.utils.respondWithResult

fun Route.dashboardRoutes() {
    val restaurantGateway: IRestaurantGateway by inject()

    authenticate("auth-jwt") {
        route("/dashboard") {
            get("/restaurant-permission-request") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java) ?: emptyList()
                val result = restaurantGateway.getAllRequestPermission(permissions)

                respondWithResult(HttpStatusCode.OK, result)
            }
        }
    }
}