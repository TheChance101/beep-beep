package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import java.util.*

fun Route.orderRoutes() {
    val restaurantGateway: IRestaurantGateway by inject()

    route("/order") {
        authenticate("auth-jwt") {
            get("/count-by-days-back") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java) ?: emptyList()
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

            get("/history/{id}") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java) ?: emptyList()
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
                val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java) ?: emptyList()
                val id = call.parameters["id"]?.trim().toString()
                val params = call.receiveParameters()
                val status = params["status"]?.trim()?.toInt() ?: 0
                val (language, countryCode) = extractLocalizationHeader()
                val result = restaurantGateway.updateOrderStatus(
                    orderId = id, permissions = permissions, status = status, locale = Locale(language, countryCode)
                )
                respondWithResult(HttpStatusCode.OK, result)
            }
        }
    }
}