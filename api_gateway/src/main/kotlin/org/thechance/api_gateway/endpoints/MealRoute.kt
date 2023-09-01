package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.restaurant.Meal
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Role
import java.util.*

fun Route.mealRoute() {
    val restaurantGateway: IRestaurantGateway by inject()

    route("/meal") {
        authenticateWithRole(Role.RESTAURANT_OWNER) {

            get ("/{mealId}"){
                val (language, countryCode) = extractLocalizationHeader()
                val mealId = call.parameters["mealId"]?.trim().toString()
                val meal = restaurantGateway.getMeal(mealId, Locale(language, countryCode))
                respondWithResult(HttpStatusCode.OK, meal)
            }

            post {
                val (language, countryCode) = extractLocalizationHeader()
                val meal = call.receive<Meal>()
                val createdMeal = restaurantGateway.addMeal(meal, Locale(language, countryCode))
                respondWithResult(HttpStatusCode.Created, createdMeal)
            }

            put {
                val (language, countryCode) = extractLocalizationHeader()
                val meal = call.receive<Meal>()
                val updatedMeal = restaurantGateway.updateMeal(meal, Locale(language, countryCode))
                respondWithResult(HttpStatusCode.OK, updatedMeal)
            }
        }
    }

}