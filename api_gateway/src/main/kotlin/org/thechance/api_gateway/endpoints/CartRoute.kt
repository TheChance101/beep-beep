package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.MealRequestDto
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Claim
import org.thechance.api_gateway.util.Role


fun Route.cartRoutes() {
    val restaurantService: RestaurantService by inject()

    authenticateWithRole(Role.END_USER) {
        route("/cart") {

            get {
                val language = extractLocalizationHeader()
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val result = restaurantService.getUserCart(userId, language)
                respondWithResult(HttpStatusCode.OK, result)
            }

            put {
                val language = extractLocalizationHeader()
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val meal = call.receive<MealRequestDto>()
                val result = restaurantService.updateMealInCart(meal.copy(userId = userId), language)
                respondWithResult(HttpStatusCode.OK, result)
            }

            delete("/orderNow") {
                val language = extractLocalizationHeader()
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val result = restaurantService.orderCart(userId,language)
                respondWithResult(HttpStatusCode.OK, result)
            }

        }
    }
}