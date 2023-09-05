package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.restaurant.RestaurantRequestPermissionDto
import org.thechance.api_gateway.data.service.IdentityService
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Role

fun Route.permissionRoutes() {

    val identityService: IdentityService by inject()
    val restaurantService: RestaurantService by inject()

    route("/dashboard"){

            authenticateWithRole(Role.DASHBOARD_ADMIN) {
                get("/restaurant-request") {
                    val language = extractLocalizationHeader()
                    val result = restaurantService.getAllRequestPermission(language)
                    respondWithResult(HttpStatusCode.OK, result)
                }
                post("user/{userId}/permission"){
                    val userId = call.parameters["userId"]?.trim().toString()
                    val permission = call.receiveParameters()["permission"]?.trim()?.toInt() ?: 0
                    val result = identityService.updateUserPermission(userId,permission)
                    respondWithResult(HttpStatusCode.Created, result)
                }
                put("user/{userId}/permission"){
                    val userId = call.parameters["userId"]?.trim().toString()
                    val permission = call.receiveParameters()["permission"]?.trim()?.toInt() ?: 0
                    val result = identityService.updateUserPermission(userId,permission)
                    respondWithResult(HttpStatusCode.Created, result)
                }
            }
    }

    route("/permission") {

        post("/restaurant") {
            val requestedForm = call.receive<RestaurantRequestPermissionDto>()
            val language = extractLocalizationHeader()
            val result = restaurantService.createRequestPermission(requestedForm, language)
            respondWithResult(HttpStatusCode.Created, result)
        }

        authenticateWithRole(Role.DASHBOARD_ADMIN) {
            get("/restaurant-request") {
                val language = extractLocalizationHeader()
                val result = restaurantService.getAllRequestPermission(language)
                respondWithResult(HttpStatusCode.OK, result)
            }
        }
    }
}