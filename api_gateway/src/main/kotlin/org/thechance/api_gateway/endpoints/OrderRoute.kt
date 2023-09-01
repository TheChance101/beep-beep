package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.endpoints.utils.*
import org.thechance.api_gateway.util.Role
import java.util.*

fun Route.orderRoutes() {

    val webSocketServerHandler: WebSocketServerHandler by inject()
    val restaurantService: RestaurantService by inject()

    authenticateWithRole(Role.RESTAURANT_OWNER) {
        route("/orders") {

            webSocket("/{restaurantId}") {
                val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
                val (language, countryCode) = extractLocalizationHeaderFromWebSocket()
                val orders = restaurantService.restaurantOrders(restaurantId, Locale(language, countryCode))
                webSocketServerHandler.sessions[restaurantId] = this
                webSocketServerHandler.sessions[restaurantId]?.let {
                    webSocketServerHandler.tryToCollectFormWebSocket(
                        orders,
                        it
                    )
                }
            }

            get("/{restaurantId}") {
                val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
                val (language, countryCode) = extractLocalizationHeader()
                val result = restaurantService.getActiveOrders(restaurantId, Locale(language, countryCode))
                respondWithResult(HttpStatusCode.OK, result)
            }


            get("/history/{id}") {
                val id = call.parameters["id"]?.trim().toString()
                val page = call.parameters["page"]?.trim()?.toInt() ?: 1
                val limit = call.parameters["limit"]?.trim()?.toInt() ?: 10
                val (language, countryCode) = extractLocalizationHeader()
                val result = restaurantService.getOrdersHistory(
                    restaurantId = id,
                    page = page,
                    limit = limit,
                    locale = Locale(language, countryCode)
                )
                respondWithResult(HttpStatusCode.OK, result)
            }

            get("/count-by-days-back") {
                val id = call.parameters["restaurantId"]?.trim().toString()
                val daysBack = call.parameters["daysBack"]?.trim()?.toInt() ?: 7
                val (language, countryCode) = extractLocalizationHeader()
                val result = restaurantService.getOrdersCountByDaysBefore(
                    restaurantId = id,
                    daysBack = daysBack,
                    locale = Locale(language, countryCode)
                )
                respondWithResult(HttpStatusCode.OK, result)
            }

            get("/revenue-by-days-back") {
                val id = call.parameters["restaurantId"]?.trim().toString()
                val daysBack = call.parameters["daysBack"]?.trim()?.toInt() ?: 7
                val (language, countryCode) = extractLocalizationHeader()
                val result = restaurantService.getOrdersRevenueByDaysBefore(
                    restaurantId = id,
                    daysBack = daysBack,
                    locale = Locale(language, countryCode)
                )
                respondWithResult(HttpStatusCode.OK, result)
            }

            put("/{id}/status") {
                val id = call.parameters["id"]?.trim().toString()
                val params = call.receiveParameters()
                val status = params["status"]?.trim()?.toInt() ?: 0
                val (language, countryCode) = extractLocalizationHeader()
                val result = restaurantService.updateOrderStatus(id, status, Locale(language, countryCode))
                respondWithResult(HttpStatusCode.OK, result)
            }

            delete("/{restaurantId}") {
                val restaurantId = call.parameters["restaurantId"]?.trim().toString()
                val (language, countryCode) = extractLocalizationHeader()
                val result = restaurantService.deleteRestaurant(restaurantId, Locale(language, countryCode))
                respondWithResult(HttpStatusCode.OK, result)
            }
        }
    }

}