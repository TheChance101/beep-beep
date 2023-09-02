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

fun Route.mealRoute() {
    val restaurantService: RestaurantService by inject()

    route("/meal") {
        authenticateWithRole(Role.RESTAURANT_OWNER) {

            get("/{mealId}") {
                val language = extractLocalizationHeader()
                val mealId = call.parameters["mealId"]?.trim().toString()
                val meal = restaurantService.getMeal(mealId, language)
                respondWithResult(HttpStatusCode.OK, meal)
            }

            post {
                val language = extractLocalizationHeader()
                val mealDto = call.receive<MealDto>()
                val createdMeal = restaurantService.addMeal(mealDto, language)
                respondWithResult(HttpStatusCode.Created, createdMeal)
            }

            put {
                val language = extractLocalizationHeader()
                val mealDto = call.receive<MealDto>()
                val updatedMeal = restaurantService.updateMeal(mealDto, language)
                respondWithResult(HttpStatusCode.OK, updatedMeal)
            }
        }
    }

}