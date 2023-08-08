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
import org.thechance.service_restaurant.domain.usecase.IControlRestaurant
import org.thechance.service_restaurant.domain.usecase.IDiscoverRestaurantUseCase
import org.thechance.service_restaurant.domain.usecase.IManageRestaurantDetailsUseCase
import org.thechance.service_restaurant.utils.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.utils.MissingParameterException

fun Route.restaurantRoutes() {

    val controlRestaurant: IControlRestaurant by inject()
    val manageRestaurantDetails: IManageRestaurantDetailsUseCase by inject()
    val discoverRestaurant: IDiscoverRestaurantUseCase by inject()

    get("/restaurants") {
        val page = call.parameters.extractInt("page") ?: 1
        val limit = call.parameters.extractInt("limit") ?: 10
        val restaurants = discoverRestaurant.getRestaurants(page, limit).toDto()
        call.respond(HttpStatusCode.OK, restaurants)
    }

    route("/restaurant") {

        get("/{id}") {
            val restaurantId = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val restaurant = manageRestaurantDetails.getRestaurant(restaurantId).toDetailsDto()
            call.respond(HttpStatusCode.OK, restaurant)
        }

        get("/{id}/categories") {
            val restaurantId =
                call.parameters.extractString("id") ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val category = manageRestaurantDetails.getCategoriesInRestaurant(restaurantId).toDto()
            call.respond(HttpStatusCode.OK, category)
        }

        post {
            val restaurant = call.receive<RestaurantDto>()
            val result = controlRestaurant.createRestaurant(restaurant.toEntity())
            call.respond(HttpStatusCode.Created, result)
        }

        post("/{id}/categories") {
            val restaurantId =
                call.parameters.extractString("id") ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val categoryIds = call.receive<List<String>>()
            val result = manageRestaurantDetails.addCategoryToRestaurant(restaurantId, categoryIds)
            call.respond(HttpStatusCode.Created, result)
        }

        put {
            val restaurant = call.receive<RestaurantDto>()
            val result = manageRestaurantDetails.updateRestaurant(restaurant.toEntity())
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}/categories") {
            val restaurantId =
                call.parameters.extractString("id") ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val categoryIds = call.receive<List<String>>()
            val result = manageRestaurantDetails.deleteCategoriesInRestaurant(restaurantId, categoryIds)
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            val restaurantId = call.parameters["id"] ?: throw MissingParameterException(INVALID_REQUEST_PARAMETER)
            val result = controlRestaurant.deleteRestaurant(restaurantId)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}