package org.thechance.api_gateway.endpoints


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.restaurant.RestaurantDto
import org.thechance.api_gateway.data.service.IdentityService
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.data.model.restaurant.getRestaurantOptions
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Claim.USER_ID
import org.thechance.api_gateway.util.Role

fun Route.restaurantRoutes() {

    val restaurantService: RestaurantService by inject()
    val identityService: IdentityService by inject()

    route("/restaurants") {

        get {
            val language = extractLocalizationHeader()
            val restaurants =
                restaurantService.getRestaurants(getRestaurantOptions(call.parameters), languageCode = language)
            respondWithResult(HttpStatusCode.OK, restaurants)
        }

        authenticateWithRole(Role.RESTAURANT_OWNER) {
            get("/mine") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val ownerId = tokenClaim?.get(USER_ID).toString()
                val language = extractLocalizationHeader()
                val result = restaurantService.getRestaurantsByOwnerId(
                    ownerId = ownerId, languageCode = language
                )
                respondWithResult(HttpStatusCode.OK, result)
            }
        }

        get("/search") {
            val query = call.parameters["query"]
            val page = call.parameters["page"]
            val limit = call.parameters["limit"]
            val language = extractLocalizationHeader()
            val result = restaurantService.search(
                query, page, limit, languageCode = language
            )
            respondWithResult(HttpStatusCode.OK, result)
        }
    }

    route("/restaurant") {

        get("/{restaurantId}/meals") {
            val language = extractLocalizationHeader()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val restaurantId = call.parameters["restaurantId"]?.trim().toString()
            val meals = restaurantService.getMealsByRestaurantId(
                restaurantId = restaurantId,
                page = page,
                limit = limit,
                languageCode = language
            )
            respondWithResult(HttpStatusCode.OK, meals)
        }

        get("/{restaurantId}") {
            val language = extractLocalizationHeader()
            val restaurantId = call.parameters["restaurantId"]?.trim().toString()
            val restaurant = restaurantService.getRestaurantInfo(
                languageCode = language, restaurantId = restaurantId
            )
            respondWithResult(HttpStatusCode.OK, restaurant)
        }

        authenticateWithRole(Role.DASHBOARD_ADMIN) {
            post {
                val language = extractLocalizationHeader()
                val restaurantDto = call.receive<RestaurantDto>()
                val user = identityService.getUserByUsername(restaurantDto.ownerUserName, language)
                identityService.updateUserPermission(
                    userId = user.id, permission = listOf(Role.RESTAURANT_OWNER), language
                )
                val newRestaurant = restaurantService.addRestaurant(restaurantDto.copy(ownerId = user.id), language)
                respondWithResult(HttpStatusCode.Created, newRestaurant)
            }

            put {
                val language = extractLocalizationHeader()
                val restaurantDto = call.receive<RestaurantDto>()
                val updatedRestaurant = restaurantService.updateRestaurant(
                    restaurantDto, isAdmin = true, languageCode = language
                )
                respondWithResult(HttpStatusCode.OK, updatedRestaurant)
            }

            delete("/{restaurantId}") {
                val language = extractLocalizationHeader()
                val restaurantId = call.parameters["restaurantId"]?.trim().toString()
                val result = restaurantService.deleteRestaurant(restaurantId, language)
                respondWithResult(HttpStatusCode.Created, result)

            }
        }

        authenticateWithRole(Role.RESTAURANT_OWNER) {
            put("/details") {
                val language = extractLocalizationHeader()
                val restaurantDto = call.receive<RestaurantDto>()
                val tokenClaim = call.principal<JWTPrincipal>()
                val ownerId = tokenClaim?.get(USER_ID).toString()
                val updatedRestaurant = restaurantService.updateRestaurant(
                    languageCode = language,
                    isAdmin = false,
                    restaurantDto = restaurantDto.copy(ownerId = ownerId)
                )
                respondWithResult(HttpStatusCode.OK, updatedRestaurant)
            }
        }
    }
}