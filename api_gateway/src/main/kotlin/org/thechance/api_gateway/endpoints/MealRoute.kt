package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.restaurant.MealResource
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import java.util.*

fun Route.mealRoute() {
    val restaurantGateway: IRestaurantGateway by inject()

    route("meal") {
        authenticate("auth-jwt") {

            post {
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java) ?: emptyList()
                val (language, countryCode) = extractLocalizationHeader()
                val meal = call.receive<MealResource>()
                val createdMeal = restaurantGateway.addMeal(meal, permissions, Locale(language, countryCode))
                respondWithResult(HttpStatusCode.Created, createdMeal)
            }

            put {
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java) ?: emptyList()
                val (language, countryCode) = extractLocalizationHeader()
                val meal = call.receive<MealResource>()
                val updatedMeal = restaurantGateway.updateMeal(meal, permissions, Locale(language, countryCode))
                respondWithResult(HttpStatusCode.OK, updatedMeal)
            }

        }
    }

}