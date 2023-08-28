package org.thechance.api_gateway.endpoints


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.utils.*
import java.util.*

fun Route.restaurantRoutes() {

    val restaurantGateway: IRestaurantGateway by inject()


    route("/restaurant") {

        authenticate("auth-jwt") {

            route("/orders") {

                webSocket("/{restaurantId}") {
                    val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
                    val permissions = extractPermissionsFromWebSocket()
                    val (language, countryCode) = extractLocalizationHeaderFromWebSocket()
                    val orders = restaurantGateway.restaurantOrders(permissions, restaurantId, Locale(language, countryCode))
                    orders.collect { this.sendSerialized(it) }
                }

                get("/{restaurantId}") {
                    val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
                    val permissions = extractPermissions()
                    val (language, countryCode) = extractLocalizationHeader()
                    val result = restaurantGateway.getActiveOrders(permissions, restaurantId, Locale(language, countryCode))
                    respondWithResult(HttpStatusCode.OK, result)
                }

            }
        }
    }
}