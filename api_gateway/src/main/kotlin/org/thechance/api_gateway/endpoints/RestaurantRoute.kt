package org.thechance.api_gateway.endpoints


import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeaderFromWebSocket
import java.util.*

fun Route.restaurantRoutes() {

    val restaurantGateway: IRestaurantGateway by inject()


    route("/restaurant") {

        authenticate("auth-jwt") {

            webSocket("/orders/{restaurantId}") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java) ?: emptyList()
                val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
                val (language, countryCode) = extractLocalizationHeaderFromWebSocket()
                val result = restaurantGateway.restaurantOrders(permissions, restaurantId, Locale(language, countryCode))
                result.collect { this.sendSerialized(it) }
            }

        }
    }
}