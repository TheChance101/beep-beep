package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.AddressDto
import org.thechance.service_restaurant.api.models.RestaurantDto
import org.thechance.service_restaurant.api.models.mappers.toDetailsDto
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.api.utils.extractInt
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.domain.usecase.restaurant.ManageRestaurantDetailsUseCase
import org.thechance.service_restaurant.domain.usecase.restaurant.ManageRestaurantUseCase

fun Route.restaurantRoutes() {

    val manageRestaurant: ManageRestaurantUseCase by inject()
    val manageDetailsRestaurant: ManageRestaurantDetailsUseCase by inject()

    route("/restaurants") {
        get {
            val page = call.parameters.extractInt("page") ?: 1
            val limit = call.parameters.extractInt("limit") ?: 10
            val restaurants = manageRestaurant.getRestaurants(page, limit).toDto()
            call.respond(HttpStatusCode.OK, restaurants)
        }
    }

    route("/restaurant") {

        get("/{id}") {
            val restaurantId = call.parameters["id"] ?: ""
            val restaurant = manageDetailsRestaurant.getRestaurantDetails(restaurantId).toDetailsDto()
            call.respond(HttpStatusCode.OK, restaurant)
        }

        get("/{id}/categories") {
            val restaurantId = call.parameters.extractString("id") ?: ""
            val category = manageRestaurant.getCategoriesInRestaurant(restaurantId).toDto()
            call.respond(HttpStatusCode.OK, category)
        }

        get("/{id}/cuisines") {
            val restaurantId = call.parameters.extractString("id") ?: ""
            val cuisines = manageRestaurant.getCuisinesInRestaurant(restaurantId).toDto()
            call.respond(HttpStatusCode.OK, cuisines)
        }

        post {
            val restaurant = call.receive<RestaurantDto>()
            val result = manageRestaurant.createRestaurant(restaurant.toEntity())
            call.respond(HttpStatusCode.Created, result)
        }

        post("/{id}/categories") {
            val restaurantId = call.parameters.extractString("id") ?: ""
            val categoryIds = call.receive<List<String>>()
            val result = manageRestaurant.addCategoryToRestaurant(restaurantId, categoryIds)
            call.respond(HttpStatusCode.Created, result)
        }

        post("/{id}/addresses") {
            val restaurantId = call.parameters["id"] ?: ""
            val addresses = call.receive<AddressDto>()
            val result = manageDetailsRestaurant.addAddressToRestaurant(restaurantId, addresses.toEntity())
            call.respond(HttpStatusCode.OK, result)
        }

        put {
            val restaurant = call.receive<RestaurantDto>()
            val result = manageRestaurant.updateRestaurant(restaurant.toEntity())
            call.respond(HttpStatusCode.OK, result)
        }

        put("/{id}/address") {
            val restaurantId = call.parameters["id"] ?: ""
            val address = call.receive<AddressDto>()
            val result = manageDetailsRestaurant.updateAddressToRestaurant(restaurantId, address.toEntity())
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            val restaurantId = call.parameters["id"] ?: ""
            val result = manageRestaurant.deleteRestaurant(restaurantId)
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}/categories") {
            val restaurantId = call.parameters.extractString("id") ?: ""
            val categoryIds = call.receive<List<String>>()
            val result = manageRestaurant.deleteCategoriesInRestaurant(restaurantId, categoryIds)
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}/addresses") {
            val restaurantId = call.parameters["id"] ?: ""
            val addressesIds = call.receive<List<String>>()
            val result = manageDetailsRestaurant.deleteAddressToRestaurant(restaurantId, addressesIds)
            call.respond(HttpStatusCode.OK, result)
        }

    }
}