package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.restaurant.OrderDto
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.endpoints.utils.*
import org.thechance.api_gateway.util.Claim
import org.thechance.api_gateway.util.Role

fun Route.orderRoutes() {

    val webSocketServerHandler: WebSocketServerHandler by inject()
    val restaurantService: RestaurantService by inject()

    authenticateWithRole(Role.RESTAURANT_OWNER) {

        webSocket("orders/{restaurantId}") {
            val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
            val language = extractLocalizationHeaderFromWebSocket()
            val orders = restaurantService.restaurantOrders(restaurantId, language)
            webSocketServerHandler.sessions[restaurantId] = this
            webSocketServerHandler.sessions[restaurantId]?.let {
                webSocketServerHandler.tryToCollectFormWebSocket(orders, it)
            }
        }

        get("orders/active/{restaurantId}") {
            val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
            val language = extractLocalizationHeader()
            val result = restaurantService.getActiveOrders(restaurantId, language)
            respondWithResult(HttpStatusCode.OK, result)
        }

        get("orders/restaurant/{restaurantId}/history") {
            val restaurantId = call.parameters["restaurantId"]?.trim().toString()
            val page = call.parameters["page"]?.trim()?.toInt() ?: 1
            val limit = call.parameters["limit"]?.trim()?.toInt() ?: 10
            val language = extractLocalizationHeader()
            val result = restaurantService.getOrdersHistoryInRestaurant(
                restaurantId = restaurantId,
                page = page,
                limit = limit,
                languageCode = language
            )
            respondWithResult(HttpStatusCode.OK, result)
        }

        get("orders/{restaurantId}/count-by-days-back") {
            val id = call.parameters["restaurantId"]?.trim().toString()
            val daysBack = call.parameters["daysBack"]?.trim()?.toInt() ?: 7
            val language = extractLocalizationHeader()
            val result = restaurantService.getOrdersCountByDaysBefore(
                restaurantId = id,
                daysBack = daysBack,
                languageCode = language
            )
            respondWithResult(HttpStatusCode.OK, result)
        }

        get("orders/{restaurantId}/revenue-by-days-back") {
            val id = call.parameters["restaurantId"]?.trim().toString()
            val daysBack = call.parameters["daysBack"]?.trim()?.toInt() ?: 7
            val language = extractLocalizationHeader()
            val result = restaurantService.getOrdersRevenueByDaysBefore(
                restaurantId = id,
                daysBack = daysBack,
                languageCode = language
            )
            respondWithResult(HttpStatusCode.OK, result)
        }

        put("order/{orderId}") {
            val orderId = call.parameters["orderId"]?.trim().toString()
            val status = call.parameters["status"]?.trim()?.toInt() ?: 0
            val language = extractLocalizationHeader()
            val result = restaurantService.updateOrderStatus(orderId, status, language)
            respondWithResult(HttpStatusCode.OK, result)
        }

    }

    authenticateWithRole(Role.END_USER) {

        post("order") {
            val language = extractLocalizationHeader()
            val order = call.receive<OrderDto>()
            val result = restaurantService.createOrder(order, language)
            respondWithResult(HttpStatusCode.Created, result)
        }

        get("orders/user/history") {
            val tokenClaim = call.principal<JWTPrincipal>()
            val userId = tokenClaim?.get(Claim.USER_ID).toString()
            val page = call.parameters["page"]?.trim()?.toInt() ?: 1
            val limit = call.parameters["limit"]?.trim()?.toInt() ?: 10
            val language = extractLocalizationHeader()
            val result = restaurantService.getOrdersHistoryForUser(
                userId = userId,
                page = page,
                limit = limit,
                languageCode = language
            )
            respondWithResult(HttpStatusCode.OK, result)
        }
    }
}
