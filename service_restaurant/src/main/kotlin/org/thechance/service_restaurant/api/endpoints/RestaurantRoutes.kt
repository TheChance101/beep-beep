package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.BasePaginationResponseDto
import org.thechance.service_restaurant.api.models.RestaurantDto
import org.thechance.service_restaurant.api.models.mappers.toDetailsDto
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.api.models.mappers.toMealDto
import org.thechance.service_restaurant.api.utils.extractInt
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.domain.usecase.IControlRestaurantsUseCase
import org.thechance.service_restaurant.domain.usecase.IDiscoverRestaurantUseCase
import org.thechance.service_restaurant.domain.usecase.IManageRestaurantDetailsUseCase
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

fun Route.restaurantRoutes() {

    val controlRestaurant: IControlRestaurantsUseCase by inject()
    val manageRestaurantDetails: IManageRestaurantDetailsUseCase by inject()
    val discoverRestaurant: IDiscoverRestaurantUseCase by inject()

    route("restaurants") {

        get {
            val page = call.parameters.extractInt("page") ?: 1
            val limit = call.parameters.extractInt("limit") ?: 10
            val restaurants = discoverRestaurant.getRestaurants(page, limit).toDto()
            val total = controlRestaurant.getTotalNumberOfRestaurant()
            call.respond(HttpStatusCode.OK, BasePaginationResponseDto<RestaurantDto>(restaurants, total))
        }

        get("/{ownerId}") {
            val ownerId = call.parameters["ownerId"] ?: throw MultiErrorException(
                listOf(
                    INVALID_REQUEST_PARAMETER
                )
            )
            val restaurants = discoverRestaurant.getRestaurantsByOwnerId(ownerId)
            call.respond(HttpStatusCode.OK, restaurants.toDto())
        }

    }

    route("/restaurant") {

        get("/{id}/meals") {
            val restaurantId = call.parameters["id"] ?: throw MultiErrorException(
                listOf(
                    INVALID_REQUEST_PARAMETER
                )
            )
            val page = call.parameters.extractInt("page") ?: 1
            val limit = call.parameters.extractInt("limit") ?: 10
            val restaurant = discoverRestaurant.getMealsByRestaurantId(
                restaurantId = restaurantId,
                page = page,
                limit = limit
            ).toMealDto()
            call.respond(HttpStatusCode.OK, restaurant)
        }

        get("/{id}") {
            val restaurantId = call.parameters["id"] ?: throw MultiErrorException(
                listOf(
                    INVALID_REQUEST_PARAMETER
                )
            )
            val restaurant = manageRestaurantDetails.getRestaurant(restaurantId).toDetailsDto()
            call.respond(HttpStatusCode.OK, restaurant)
        }

        post {
            val restaurant = call.receive<RestaurantDto>()
            val result = controlRestaurant.createRestaurant(restaurant.toEntity())
            call.respond(HttpStatusCode.Created, result.toDto())
        }

        put("/details") {
            val restaurant = call.receive<RestaurantDto>()
            val result = manageRestaurantDetails.updateRestaurant(restaurant.toEntity())
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        put {
            val restaurant = call.receive<RestaurantDto>()
            val result = controlRestaurant.updateRestaurant(restaurant.toEntity())
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        delete("/{id}/categories") {
            val restaurantId =
                call.parameters.extractString("id") ?: throw MultiErrorException(
                    listOf(
                        INVALID_REQUEST_PARAMETER
                    )
                )
            val categoryIds = call.receive<List<String>>()
            val result =
                manageRestaurantDetails.deleteCategoriesInRestaurant(restaurantId, categoryIds)
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            val restaurantId = call.parameters["id"] ?: throw MultiErrorException(
                listOf(
                    INVALID_REQUEST_PARAMETER
                )
            )
            val result = controlRestaurant.deleteRestaurant(restaurantId)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}