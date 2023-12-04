package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.localizedMessages.LocalizedMessagesFactory
import org.thechance.api_gateway.data.model.notification.NotificationDto
import org.thechance.api_gateway.data.model.notification.NotificationSender
import org.thechance.api_gateway.data.model.restaurant.OrderStatus
import org.thechance.api_gateway.data.service.NotificationService
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.endpoints.utils.WebSocketServerHandler
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Claim
import org.thechance.api_gateway.util.Role

fun Route.orderRoutes() {

    val webSocketServerHandler: WebSocketServerHandler by inject()
    val restaurantService: RestaurantService by inject()
    val notificationService: NotificationService by inject()
    val localizedMessagesFactory by inject<LocalizedMessagesFactory>()

    authenticateWithRole(Role.RESTAURANT_OWNER) {

        webSocket("orders/{restaurantId}") {
            val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
            val orders = restaurantService.getIncomingOrders(restaurantId)
            webSocketServerHandler.sessions[restaurantId] = this
            webSocketServerHandler.sessions[restaurantId]?.let { session ->
                webSocketServerHandler.tryToCollectOrders(values = orders, session = session)
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
            val language = extractLocalizationHeader()
            val result = restaurantService.updateOrderStatus(orderId, language)
            respondWithResult(HttpStatusCode.OK, result)

            if (result.orderStatus != OrderStatus.PENDING.statusCode ||
                result.orderStatus != OrderStatus.CANCELED.statusCode
            ) {
                val orderStatus = OrderStatus.getOrderStatus(result.orderStatus!!)
                val notificationMessage = when (orderStatus) {
                    OrderStatus.APPROVED -> localizedMessagesFactory.createLocalizedMessages(language).orderApproved
                    OrderStatus.IN_COOKING -> localizedMessagesFactory.createLocalizedMessages(language).orderInCooking
                    OrderStatus.DONE -> localizedMessagesFactory.createLocalizedMessages(language).orderFinished
                    else -> ""
                }
                val orderNotification = NotificationDto(
                    title = result.restaurantName!!,
                    body = notificationMessage,
                    userId = result.userId,
                    topicId = result.id,
                    sender = NotificationSender.RESTAURANT.code
                )
                notificationService.sendNotificationToUser(orderNotification, language)
            }
        }

        put("order/cancel/{orderId}") {
            val orderId = call.parameters["orderId"]?.trim().toString()
            val language = extractLocalizationHeader()
            val result = restaurantService.cancelOrder(orderId, language)
            respondWithResult(HttpStatusCode.OK, result)
            val notificationMessage = localizedMessagesFactory.createLocalizedMessages(language).orderCanceled
            val orderNotification = NotificationDto(
                title = result.restaurantName!!,
                body = notificationMessage,
                userId = result.userId,
                topicId = result.id,
                sender = NotificationSender.RESTAURANT.code
            )
            notificationService.sendNotificationToUser(orderNotification, language)
        }
    }

    authenticateWithRole(Role.END_USER) {

        get("order/{orderId}") {
            val orderId = call.parameters["orderId"]?.trim().toString()
            val language = extractLocalizationHeader()
            val result = restaurantService.getOrderById(orderId, language)
            respondWithResult(HttpStatusCode.OK, result)
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

        webSocket("order/track/{orderId}") {
            val orderId = call.parameters["orderId"]?.trim().orEmpty()
            val order = restaurantService.trackOrder(orderId)
            webSocketServerHandler.sessions[orderId] = this
            webSocketServerHandler.sessions[orderId]?.let {
                webSocketServerHandler.tryToCollect(order, it)
            }
        }

        get("active/orders") {
            val tokenClaim = call.principal<JWTPrincipal>()
            val userId = tokenClaim?.get(Claim.USER_ID).toString()
            val language = extractLocalizationHeader()
            val result = restaurantService.getActiveOrdersForUser(userId, language)
            respondWithResult(HttpStatusCode.OK, result)
        }
    }
}