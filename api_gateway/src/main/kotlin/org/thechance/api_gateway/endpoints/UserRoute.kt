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
import org.thechance.api_gateway.data.model.identity.getUserOptions
import org.thechance.api_gateway.data.model.taxi.toDeliveryTripResponse
import org.thechance.api_gateway.data.service.IdentityService
import org.thechance.api_gateway.data.service.NotificationService
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
    val notificationService: NotificationService by inject()

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

            get("/profile") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val username = tokenClaim?.get(Claim.USERNAME).toString()
                val language = extractLocalizationHeader()
                val user = identityService.getUserByUsername(username, language)
                respondWithResult(HttpStatusCode.OK, user)
            }
            get("/addresses") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val language = extractLocalizationHeader()
                val userAddresses = identityService.getUserAddresses(userId, language)
                respondWithResult(HttpStatusCode.OK, userAddresses)
            }

            put("/profile") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val language = extractLocalizationHeader()
                val params = call.receiveParameters()
                val fullName = params["fullName"]?.trim()
                val phone = params["phone"]?.trim()
                val result = identityService.updateUserProfile(userId, fullName, phone, language)
                respondWithResult(HttpStatusCode.OK, result)
            }

            put("/location") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val language = extractLocalizationHeader()
                val location = call.receive<LocationDto>()
                val userLocation = identityService.updateUserLocation(userId, location, language)
                respondWithResult(HttpStatusCode.OK, userLocation)
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

            get("active/taxi/trips") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val language = extractLocalizationHeader()
                val trips = taxiService.getActiveTripsByUserId(userId, language).filter { it.isATaxiTrip == true }
                respondWithResult(HttpStatusCode.OK, trips)
            }

            get("active/delivery/trips") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val language = extractLocalizationHeader()
                val trips = taxiService.getActiveTripsByUserId(userId, language).filter { it.isATaxiTrip == false }
                val restaurantIds = trips.mapNotNull { it.restaurantId }.distinct()
                val restaurantInfo = restaurantService.getRestaurants(restaurantIds, language)
                val restaurantInfoMap = restaurantInfo.associateBy { it.id }
                val deliveryTrips = trips.mapNotNull { tripDto ->
                    val restaurant = restaurantInfoMap[tripDto.restaurantId]
                    restaurant?.let { tripDto.toDeliveryTripResponse(it) }
                }
                respondWithResult(HttpStatusCode.OK, deliveryTrips)
            }

            //TODO: delete when Done just for test now.
            put("/permissionToUser") {
                val language = extractLocalizationHeader()
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val permission: List<Int> = call.receive<List<Int>>()
                val result = identityService.updateUserPermission(userId, permission, language)
                respondWithResult(HttpStatusCode.OK, result)
            }
        }
    }

    delete("/clearDB") {
        val resultIdentity = identityService.clearIdentityDB()
        restaurantService.deleteAllCollections()
        notificationService.deleteNotificationCollection()
        val resultTaxi = taxiService.deleteTaxiAndTripsCollections()
        val result = resultIdentity.and(resultTaxi)
        respondWithResult(HttpStatusCode.OK, result)
    }
}

