package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.service.IdentityService
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Claim
import org.thechance.api_gateway.util.Role

fun Route.userRoutes() {
    val identityService: IdentityService by inject()
    val restaurantService: RestaurantService by inject()

    authenticateWithRole(Role.DASHBOARD_ADMIN) {
        get("/users") {
            val language = extractLocalizationHeader()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val searchTerm = call.parameters["searchTerm"] ?: ""
            val result = identityService.getUsers(
                page = page,
                limit = limit,
                searchTerm = searchTerm,
                language
            )
            respondWithResult(HttpStatusCode.OK, result)
        }
    }

    route("/user") {
        authenticateWithRole(Role.DASHBOARD_ADMIN) {
            delete("/{id}") {
                val id = call.parameters["id"] ?: ""
                val language = extractLocalizationHeader()
                val result = identityService.deleteUser(userId = id, language)
                respondWithResult(HttpStatusCode.OK, result)
            }

            put("/{userId}/permission") {
                val language = extractLocalizationHeader()
                val userId = call.parameters["userId"]?.trim().toString()
                val permission: List<Int> = call.receive<List<Int>>()
                val result = identityService.updateUserPermission(userId, permission,language)
                respondWithResult(HttpStatusCode.OK, result)
            }

            get("/last-register") {
                val limit = call.parameters["limit"]?.toInt() ?: 4
                val result = identityService.getLastRegisteredUsers(limit)
                respondWithResult(HttpStatusCode.OK, result)
            }

            get("/search"){
                val searchTerm = call.request.queryParameters["query"]?.trim() ?:""
                val permission: List<Int> = call.receive<List<Int>>()
                val users = identityService.searchUsers(searchTerm, permission)
                respondWithResult(HttpStatusCode.OK, users)
            }
        }

        authenticateWithRole(Role.END_USER) {
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

