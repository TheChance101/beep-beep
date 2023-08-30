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
import org.thechance.api_gateway.data.model.restaurant.RestaurantResource
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.utils.*
import org.thechance.api_gateway.util.Role
import java.util.*

fun Route.restaurantRoutes() {

    val restaurantGateway: IRestaurantGateway by inject()
    val webSocketServerHandler: WebSocketServerHandler by inject()

    route("/restaurants") {

        authenticateWithRole(Role.RESTAURANT_OWNER) {
            get {
                val tokenClaim = call.principal<JWTPrincipal>()
                val ownerId = tokenClaim?.payload?.subject?.trim().toString()
                val (language, countryCode) = extractLocalizationHeader()
                val result = restaurantGateway.getRestaurantsByOwnerId(
                    ownerId = ownerId,
                    locale = Locale(language, countryCode)
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
            val restaurant = restaurantGateway.getRestaurantInfo(
                locale = Locale(language, countryCode), restaurantId = restaurantId
            )
            respondWithResult(HttpStatusCode.OK, restaurant)
        }
        authenticateWithRole(Role.RESTAURANT_ADMIN) {
            put {
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java) ?: emptyList()
                val (language, countryCode) = extractLocalizationHeader()
                val restaurant = call.receive<RestaurantResource>()

                val updatedRestaurant = restaurantGateway.updateRestaurantForAdmin(
                    restaurant, permissions, Locale(language, countryCode)
                )
                respondWithResult(HttpStatusCode.OK, updatedRestaurant.toRestaurant())
            }
        }
        authenticateWithRole(Role.RESTAURANT_OWNER) {
            put("/details") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java) ?: emptyList()
                val (language, countryCode) = extractLocalizationHeader()
                val restaurant = call.receive<RestaurantResource>()

                val updatedRestaurant = restaurantGateway.updateRestaurant(
                    Locale(language, countryCode),
                    restaurant,
                    permissions,
                )
                respondWithResult(HttpStatusCode.OK, updatedRestaurant.toRestaurant())
            }
        }

        authenticateWithRole(Role.RESTAURANT_OWNER) {

            route("/orders") {

                webSocket("/{restaurantId}") {
                    val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
                    val (language, countryCode) = extractLocalizationHeaderFromWebSocket()
                    val orders = restaurantGateway.restaurantOrders(restaurantId, Locale(language, countryCode))
                    webSocketServerHandler.sessions[restaurantId] = this
                    webSocketServerHandler.sessions[restaurantId]?.let { webSocketServerHandler.tryToCollectFormWebSocket(orders, it) }
                }

                get("/{restaurantId}") {
                    val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
                    val (language, countryCode) = extractLocalizationHeader()
                    val result = restaurantGateway.getActiveOrders(restaurantId, Locale(language, countryCode))
                    respondWithResult(HttpStatusCode.OK, result)
                }


                get("/history/{id}") {
                    val id = call.parameters["id"]?.trim().toString()
                    val page = call.parameters["page"]?.trim()?.toInt() ?: 1
                    val limit = call.parameters["limit"]?.trim()?.toInt() ?: 10
                    val (language, countryCode) = extractLocalizationHeader()
                    val result = restaurantGateway.getOrdersHistory(
                        restaurantId = id,
                        page = page,
                        limit = limit,
                        locale = Locale(language, countryCode)
                    )
                    respondWithResult(HttpStatusCode.OK, result)
                }

                put("/{id}/status") {
                    val id = call.parameters["id"]?.trim().toString()
                    val params = call.receiveParameters()
                    val status = params["status"]?.trim()?.toInt() ?: 0
                    val (language, countryCode) = extractLocalizationHeader()
                    val result = restaurantGateway.updateOrderStatus(id, status, Locale(language, countryCode))
                    respondWithResult(HttpStatusCode.OK, result)
                }

                delete("/{restaurantId}") {
                    val restaurantId = call.parameters["restaurantId"]?.trim().toString()
                    val (language, countryCode) = extractLocalizationHeader()
                    val result = restaurantGateway.deleteRestaurant(restaurantId, Locale(language, countryCode))
                    respondWithResult(HttpStatusCode.OK, result)
                }
            }
        }
    }
}