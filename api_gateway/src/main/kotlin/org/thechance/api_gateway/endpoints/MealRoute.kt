package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.restaurant.MealDto
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Role
import java.util.*

fun Route.mealRoute() {
    val restaurantService: RestaurantService by inject()

    route("/meal") {
        authenticateWithRole(Role.RESTAURANT_OWNER) {

            get ("/{mealId}"){
                val (language, countryCode) = extractLocalizationHeader()
                val mealId = call.parameters["mealId"]?.trim().toString()
                val meal = restaurantService.getMeal(mealId, Locale(language, countryCode))
                respondWithResult(HttpStatusCode.OK, meal)
            }

            post {
                val (language, countryCode) = extractLocalizationHeader()
                val mealDto = call.receive<MealDto>()
                val createdMeal = restaurantService.addMeal(mealDto, Locale(language, countryCode))
                respondWithResult(HttpStatusCode.Created, createdMeal)
            }

            put {
                val (language, countryCode) = extractLocalizationHeader()
                val mealDto = call.receive<MealDto>()
                val updatedMeal = restaurantService.updateMeal(mealDto, Locale(language, countryCode))
                respondWithResult(HttpStatusCode.OK, updatedMeal)
            }
        }
    }

}