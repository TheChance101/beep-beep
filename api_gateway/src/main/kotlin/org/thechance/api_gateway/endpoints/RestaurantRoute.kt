package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.mappers.toRestaurant
import org.thechance.api_gateway.data.model.restaurant.RestaurantResource
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import java.util.*

fun Route.restaurantRoutes() {
    val restaurantGateway: IRestaurantGateway by inject()

    route("/restaurants") {

        authenticate("auth-jwt") {
            get {
                val tokenClaim = call.principal<JWTPrincipal>()
                val ownerId = tokenClaim?.payload?.subject?.trim().toString()
                val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java)
                    ?: emptyList()
                val (language, countryCode) = extractLocalizationHeader()
                val result = restaurantGateway.getRestaurantsByOwnerId(
                    ownerId = ownerId,
                    locale = Locale(language, countryCode),
                    permissions = permissions
                )
                respondWithResult(HttpStatusCode.OK, result)
            }
        }
    }

    route("/restaurant") {
        authenticate("auth-jwt") {
            get {
                val (language, countryCode) = extractLocalizationHeader()
                val page = call.parameters["page"]?.toInt() ?: 1
                val limit = call.parameters["limit"]?.toInt() ?: 20
                val restaurants = restaurantGateway.getRestaurants(page, limit, locale = Locale(language, countryCode))
                respondWithResult(HttpStatusCode.OK, restaurants.toRestaurant())
            }

            get("/{id}") {
                val (language, countryCode) = extractLocalizationHeader()
                val restaurantId = call.parameters["id"]?.trim().toString()
                val restaurant =
                    restaurantGateway.getRestaurantInfo(
                        locale = Locale(language, countryCode),
                        restaurantId = restaurantId
                    )
                respondWithResult(HttpStatusCode.OK, restaurant)
            }

            delete("/{restaurantId}") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java)
                    ?: emptyList()
                val restaurantId = call.parameters["restaurantId"]?.trim().toString()
                val (language, countryCode) = extractLocalizationHeader()
                val result = restaurantGateway.deleteRestaurant(
                    restaurantId = restaurantId,
                    permissions = permissions,
                    locale = Locale(language, countryCode),
                )
                respondWithResult(HttpStatusCode.OK, result)
            }
        }

        put {
            val tokenClaim = call.principal<JWTPrincipal>()
            val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java) ?: emptyList()
            val (language, countryCode) = extractLocalizationHeader()
            val params = call.receiveParameters()
            val restaurantId = params["id"]?.trim().toString()
            val name = params["name"]?.trim().toString()
            val phone = params["phone"]?.trim().toString()
            val description = params["description"]?.trim().toString()
            val openingTime = params["openingTime"]?.trim().toString()
            val closingTime = params["closingTime"]?.trim().toString()


            val updatedRestaurant = restaurantGateway.updateRestaurantForAdmin(
                restaurantId,
                name,
                phone,
                description,
                openingTime,
                closingTime,
                permissions,
                Locale(language, countryCode)
            )
            respondWithResult(HttpStatusCode.OK, updatedRestaurant.toRestaurant())
        }

        put {
            val tokenClaim = call.principal<JWTPrincipal>()
            val permissions = tokenClaim?.payload?.getClaim("permissions")?.asList(Int::class.java) ?: emptyList()
            val (language, countryCode) = extractLocalizationHeader()
            val restaurant = call.receive<RestaurantResource>()

            val updatedRestaurant = restaurantGateway.updateRestaurant(
                Locale(language, countryCode),
                restaurant,
                permissions,
            )
            respondWithResult(HttpStatusCode.OK, updatedRestaurant.toRestaurant())
        }

   }
}
