package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.CuisineDto
import org.thechance.service_restaurant.api.models.mappers.toDetailsDto
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.api.utils.extractInt
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.domain.usecase.ClientUseCase
import org.thechance.service_restaurant.domain.usecase.AdministratorUseCase
import org.thechance.service_restaurant.domain.usecase.manageRestaurant.ManageRestaurantUseCase

fun Route.cuisineRoutes() {

    val client: ClientUseCase by inject()
    val administrator: AdministratorUseCase by inject()
    val manageRestaurant:ManageRestaurantUseCase by inject ()

    get("cuisines") {
        val page = call.parameters.extractInt("page") ?: 1
        val limit = call.parameters.extractInt("limit") ?: 10
        val cuisines = manageRestaurant.getCuisines(page, limit)
        call.respond(HttpStatusCode.OK, cuisines.toDto())
    }

    route("cuisine") {

        get("/{id}/meals") {
            val id = call.parameters.extractString("id") ?: throw NotFoundException()
            val meals = client.getMealsInCuisines(id).toDto()
            call.respond(HttpStatusCode.OK, meals)
        }

        post {
            val cuisine = call.receive<CuisineDto>()
            val isAdded = administrator.addCuisine(cuisine.toEntity())
            if (isAdded) call.respond(HttpStatusCode.Created, "Added Successfully")
        }

        put {
            val cuisine = call.receive<CuisineDto>()
            val isUpdated = administrator.updateCuisine(cuisine.toEntity())
            if (isUpdated) call.respond(HttpStatusCode.OK, "Updated Successfully")
        }

        delete("/{id}") {
            val id = call.parameters.extractString("id") ?: throw NotFoundException()
            val isDeleted = administrator.deleteCuisine(id)
            if (isDeleted) call.respond(HttpStatusCode.OK, "Deleted Successfully")
        }

    }

}