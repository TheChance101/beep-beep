package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.RestaurantDto
import org.thechance.service_restaurant.api.models.mappers.toDetailsDto
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.api.utils.extractInt
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.domain.usecase.AdministratorUseCase
import org.thechance.service_restaurant.domain.usecase.ClientUseCase
import org.thechance.service_restaurant.domain.usecase.ManageRestaurantUseCase

fun Route.restaurantRoutes() {

    val client: ClientUseCase by inject()
    val administrator: AdministratorUseCase by inject()
    val manageRestaurant: ManageRestaurantUseCase by inject()

    get("/restaurants") {
        val page = call.parameters.extractInt("page") ?: 1
        val limit = call.parameters.extractInt("limit") ?: 10
        val restaurants = client.getRestaurants(page, limit).toDto()
        call.respond(HttpStatusCode.OK, restaurants)
    }

    route("/restaurant") {

        get("/{id}") {
            val restaurantId = call.parameters["id"] ?: ""
            val restaurant = client.getRestaurantDetails(restaurantId).toDetailsDto()
            call.respond(HttpStatusCode.OK, restaurant)
        }

        get("/{id}/categories") {
            val restaurantId = call.parameters.extractString("id") ?: ""
            val category = client.getCategoriesInRestaurant(restaurantId).toDto()
            call.respond(HttpStatusCode.OK, category)
        }

        post {
            val restaurant = call.receive<RestaurantDto>()
            val result = administrator.createRestaurant(restaurant.toEntity())
            call.respond(HttpStatusCode.Created, result)
        }

        post("/{id}/categories") {
            val restaurantId = call.parameters.extractString("id") ?: ""
            val categoryIds = call.receive<List<String>>()
            val result = manageRestaurant.addCategoryToRestaurant(restaurantId, categoryIds)
            call.respond(HttpStatusCode.Created, result)
        }

        put {
            val restaurant = call.receive<RestaurantDto>()
            val result = manageRestaurant.updateRestaurant(restaurant.toEntity())
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}/categories") {
            val restaurantId = call.parameters.extractString("id") ?: ""
            val categoryIds = call.receive<List<String>>()
            val result = manageRestaurant.deleteCategoriesInRestaurant(restaurantId, categoryIds)
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            val restaurantId = call.parameters["id"] ?: ""
            val result = administrator.deleteRestaurant(restaurantId)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}