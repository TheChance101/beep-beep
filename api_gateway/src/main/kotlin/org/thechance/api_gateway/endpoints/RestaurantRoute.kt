package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.mappers.toMeal
import org.thechance.api_gateway.data.mappers.toRestaurant
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.utils.*
import java.util.*

fun Route.restaurantRoutes() {

    val restaurantGateway: IRestaurantGateway by inject()
    val webSocketServerHandler: WebSocketServerHandler by inject()

    route("/restaurants") {

        authenticate("auth-jwt") {
            get {
                val tokenClaim = call.principal<JWTPrincipal>()
                val ownerId = tokenClaim?.payload?.subject?.trim().toString()
                val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java)
                    ?: emptyList()
                val (language, countryCode) = extractLocalizationHeader()
                val result = restaurantGateway.getRestaurantsByOwnerId(
                    ownerId = ownerId,
                    locale = Locale(language, countryCode),
                    permissions = permissions
                )
                respondWithResult(HttpStatusCode.OK, result)
            }
        }
    }

    route("/restaurant") {
        get {
            val (language, countryCode) = extractLocalizationHeader()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val restaurants = restaurantGateway.getRestaurants(page, limit, locale = Locale(language, countryCode))
            respondWithResult(HttpStatusCode.OK, restaurants.toRestaurant())
        }

        get("/{id}/meals") {
            val (language, countryCode) = extractLocalizationHeader()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val restaurantId = call.parameters["id"]?.trim().toString()
            val meals = restaurantGateway.getMealsByRestaurantId(
                restaurantId = restaurantId,
                page = page,
                limit = limit,
                locale = Locale(language, countryCode)
            )
            respondWithResult(HttpStatusCode.OK, meals.map { it.toMeal() })
        }


        get("/{id}") {
            val (language, countryCode) = extractLocalizationHeader()
            val restaurantId = call.parameters["id"]?.trim().toString()
            val restaurant =
                restaurantGateway.getRestaurantInfo(locale = Locale(language, countryCode), restaurantId = restaurantId)
            respondWithResult(HttpStatusCode.OK, restaurant.toRestaurant())
        }


        authenticate("auth-jwt", "refresh-jwt") {

            route("/orders") {

                webSocket("/{restaurantId}") {
                    val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
                    val permissions = extractPermissionsFromWebSocket()
                    val (language, countryCode) = extractLocalizationHeaderFromWebSocket()
                    val orders =
                        restaurantGateway.restaurantOrders(
                            permissions,
                            restaurantId,
                            Locale(language, countryCode)
                        )
                    webSocketServerHandler.sessions[restaurantId] = this
                    webSocketServerHandler.sessions[restaurantId]?.let {
                        webSocketServerHandler.tryToCollectFormWebSocket(
                            orders,
                            it
                        )
                    }
                }

                get("/count-by-days-back") {
                    val tokenClaim = call.principal<JWTPrincipal>()
                    val permissions =
                        tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java)
                            ?: emptyList()
                    val id = call.parameters["restaurantId"]?.trim().toString()
                    val daysBack = call.parameters["daysBack"]?.trim()?.toInt() ?: 7
                    val (language, countryCode) = extractLocalizationHeader()
                    val result = restaurantGateway.getOrdersCountByDaysBefore(
                        restaurantId = id,
                        daysBack = daysBack,
                        permissions = permissions,
                        locale = Locale(language, countryCode)
                    )
                    respondWithResult(HttpStatusCode.OK, result)
                }

                get("/{restaurantId}") {
                    val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
                    val permissions = extractPermissions()
                    val (language, countryCode) = extractLocalizationHeader()
                    val result =
                        restaurantGateway.getActiveOrders(permissions, restaurantId, Locale(language, countryCode))
                    respondWithResult(HttpStatusCode.OK, result)
                }


                get("/history/{id}") {
                    val tokenClaim = call.principal<JWTPrincipal>()
                    val permissions =
                        tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java) ?: emptyList()
                    val id = call.parameters["id"]?.trim().toString()
                    val page = call.parameters["page"]?.trim()?.toInt() ?: 1
                    val limit = call.parameters["limit"]?.trim()?.toInt() ?: 10
                    val (language, countryCode) = extractLocalizationHeader()
                    val result = restaurantGateway.getOrdersHistory(
                        restaurantId = id,
                        permissions = permissions,
                        page = page,
                        limit = limit,
                        locale = Locale(language, countryCode)
                    )
                    respondWithResult(HttpStatusCode.OK, result)
                }

                put("/{id}/status") {
                    val tokenClaim = call.principal<JWTPrincipal>()
                    val permissions =
                        tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java) ?: emptyList()
                    val id = call.parameters["id"]?.trim().toString()
                    val params = call.receiveParameters()
                    val status = params["status"]?.trim()?.toInt() ?: 0
                    val (language, countryCode) = extractLocalizationHeader()
                    val result = restaurantGateway.updateOrderStatus(
                        orderId = id, permissions = permissions, status = status, locale = Locale(language, countryCode)
                    )
                    respondWithResult(HttpStatusCode.OK, result)
                }

                delete("/{restaurantId}") {
                    val tokenClaim = call.principal<JWTPrincipal>()
                    val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java)
                        ?: emptyList()
                    val restaurantId = call.parameters["restaurantId"]?.trim().toString()
                    val (language, countryCode) = extractLocalizationHeader()
                    val result = restaurantGateway.deleteRestaurant(
                        restaurantId = restaurantId,
                        permissions = permissions,
                        locale = Locale(language, countryCode),
                    )
                    respondWithResult(HttpStatusCode.OK, result)
                }
            }
        }
    }
}