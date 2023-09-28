package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.LocationDto
import org.thechance.api_gateway.data.model.getUserOptions
import org.thechance.api_gateway.data.model.restaurant.getRestaurantOptions
import org.thechance.api_gateway.data.service.IdentityService
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.data.service.TaxiService
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.hasPermission
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Claim
import org.thechance.api_gateway.util.Role

fun Route.userRoutes() {
    val identityService: IdentityService by inject()
    val restaurantService: RestaurantService by inject()
    val taxiService: TaxiService by inject()

    authenticateWithRole(Role.DASHBOARD_ADMIN) {
        get("/users") {
            val language = extractLocalizationHeader()
            val users =
                identityService.getUsers(getUserOptions(call.parameters), languageCode = language)
            respondWithResult(HttpStatusCode.OK, users)
        }
    }

    route("/user") {

        authenticateWithRole(Role.DASHBOARD_ADMIN) {
            get("/last-register") {
                val limit = call.parameters["limit"]?.toInt() ?: 4
                val result = identityService.getLastRegisteredUsers(limit)
                respondWithResult(HttpStatusCode.OK, result)
            }

            put("/{userId}/permission") {
                val language = extractLocalizationHeader()
                val userId = call.parameters["userId"]?.trim().toString()
                val permission: List<Int> = call.receive<List<Int>>()
                val result = identityService.updateUserPermission(userId, permission, language)
                respondWithResult(HttpStatusCode.OK, result)
            }

            delete("/{id}") {
                val id = call.parameters["id"] ?: ""
                val language = extractLocalizationHeader()
                val user = identityService.getUserById(id, language)
                val result = identityService.deleteUser(userId = id, language)
                if (result) {
                    if (hasPermission(user.permission, Role.TAXI_DRIVER))
                        taxiService.deleteTaxiByDriverId(user.id)
                    if (hasPermission(user.permission, Role.RESTAURANT_OWNER))
                        restaurantService.deleteRestaurantByOwnerId(user.id)
                }
                respondWithResult(HttpStatusCode.OK, result)
            }
        }


        authenticateWithRole(Role.END_USER) {
            get {
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val language = extractLocalizationHeader()
                val user = identityService.getUserById(userId, language)
                respondWithResult(HttpStatusCode.OK, user)
            }
            put("profile") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val language = extractLocalizationHeader()
                val params = call.receiveParameters()
                val fullName = params["fullName"]?.trim().toString()
                val result = identityService.updateUserProfile(userId, fullName, language)
                respondWithResult(HttpStatusCode.OK, result)
            }

            put("/location") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val language = extractLocalizationHeader()
                val location = call.receive<LocationDto>()
                val userLocation = identityService.updateUserLocation(userId, location, language)
                call.respond(HttpStatusCode.Created, userLocation)
            }

            get("/favorite") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val language = extractLocalizationHeader()
                val restaurantIds = identityService.getFavoriteRestaurantsIds(
                    userId = userId, languageCode = language
                )
                val result = async {
                    restaurantService.getRestaurants(restaurantIds, language)
                }.await()
                respondWithResult(HttpStatusCode.OK, result)
            }

            post("/favorite") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val language = extractLocalizationHeader()
                val restaurantId = call.receiveParameters()["restaurantId"].toString()
                val result = identityService.addRestaurantToFavorite(
                    userId = userId, restaurantId = restaurantId, languageCode = language
                )
                respondWithResult(HttpStatusCode.OK, result)
            }

            delete("/favorite") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val language = extractLocalizationHeader()
                val restaurantId = call.receiveParameters()["restaurantId"].toString()
                val result = identityService.deleteRestaurantFromFavorite(
                    userId = userId, restaurantId = restaurantId, languageCode = language
                )
                respondWithResult(HttpStatusCode.OK, result)
            }
        }
    }

}

