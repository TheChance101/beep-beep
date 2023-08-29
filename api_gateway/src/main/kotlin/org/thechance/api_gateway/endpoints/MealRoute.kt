package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.mappers.toMeal
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import java.util.*

fun Route.mealRoute() {
    val restaurantGateway: IRestaurantGateway by inject()

    route("meal") {
        authenticate("auth-jwt") {
            post {
                val (language, countryCode) = extractLocalizationHeader()
                val params = call.receiveParameters()
                val restaurantId = params["restaurantId"]?.trim().toString()
                val name = params["name"]?.trim().toString()
                val description = params["description"]?.trim().toString()
                val price = params["price"]?.toDoubleOrNull()
                val cuisines = params.getAll("cuisines")

                val createdMeal = restaurantGateway.addMeal(
                    restaurantId,
                    name,
                    description,
                    price!!,
                    cuisines!!,
                    Locale(language, countryCode)
                )
                respondWithResult(HttpStatusCode.Created, createdMeal.toMeal())
            }

            put {
                val (language, countryCode) = extractLocalizationHeader()
                val params = call.receiveParameters()
                val restaurantId = params["restaurantId"]?.trim().toString()
                val name = params["name"]?.trim().toString()
                val description = params["description"]?.trim().toString()
                val price = params["price"]?.toDoubleOrNull()
                val cuisines = params.getAll("cuisines")

                val updatedMeal = restaurantGateway.updateMeal(
                    restaurantId,
                    name,
                    description,
                    price!!,
                    cuisines!!,
                    Locale(language, countryCode)
                )
                respondWithResult(HttpStatusCode.OK, updatedMeal.toMeal())
            }
        }
    }

}