package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.CuisineDto
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.api.utils.extractInt
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.usecase.cuisine.CuisineUseCasesContainer

fun Route.cuisineRoutes() {

    val cuisineUseCasesContainer: CuisineUseCasesContainer by inject()

    route("cuisines") {
        get {
            val page = call.parameters.extractInt("page") ?: 1
            val limit = call.parameters.extractInt("limit") ?: 10
            val cuisines = cuisineUseCasesContainer.getCuisinesUseCase(page, limit)
            call.respond(HttpStatusCode.OK, cuisines.toDto())
        }
    }

    route("cuisine") {

        get("/{id}") {
            val id = call.parameters.extractString("id") ?: throw NotFoundException()
            val cuisine = cuisineUseCasesContainer.getCuisineByIdUseCase(id)
            call.respond(HttpStatusCode.OK, cuisine.toDto())
        }

        get("/{id}/meals") {
            val id = call.parameters.extractString("id") ?: throw NotFoundException()
            val meals = cuisineUseCasesContainer.getMealsInCuisine(id).toDto()
            call.respond(HttpStatusCode.OK, meals)
        }

        post {
            val cuisine = call.receive<CuisineDto>()
            val isAdded = cuisineUseCasesContainer.addCuisineUseCase(cuisine.toEntity())
            if (isAdded) call.respond(HttpStatusCode.Created, "Added Successfully")
        }

        post("/{id}/meals") {
            val cuisineId = call.parameters.extractString("id") ?: ""
            val mealIds = call.receive<List<String>>()
            val isAdded = cuisineUseCasesContainer.addMealsToCuisine(cuisineId, mealIds)
            if (isAdded) call.respond(HttpStatusCode.Created, "Added Successfully")
        }

        put {
            val cuisine = call.receive<CuisineDto>()
            val isUpdated = cuisineUseCasesContainer.updateCuisineUseCase(cuisine.toEntity())
            if (isUpdated) call.respond(HttpStatusCode.OK, "Updated Successfully")
        }

        delete("/{id}") {
            val id = call.parameters.extractString("id") ?: throw NotFoundException()
            val isDeleted = cuisineUseCasesContainer.deleteCuisineUseCase(id)
            if (isDeleted) call.respond(HttpStatusCode.OK, "Deleted Successfully")
        }

        delete("/{id}/meals") {
            val id = call.parameters.extractString("id") ?: throw NotFoundException()
            val mealIds = call.receive<List<String>>()
            val isDeleted = cuisineUseCasesContainer.deleteMealsInCuisine(id, mealIds)
            if (isDeleted) call.respond(HttpStatusCode.OK, "Deleted Successfully")
        }
    }

}