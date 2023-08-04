package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.MealDto
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.api.utils.extractInt
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.usecase.meal.MealUseCasesContainer

fun Route.mealRoutes() {

    val mealUseCasesContainer: MealUseCasesContainer by inject()

    route("meals") {
        get {
            val page = call.parameters.extractInt("page") ?: 1
            val limit = call.parameters.extractInt("limit") ?: 10
            val meals = mealUseCasesContainer.getMealsUseCase(page, limit)
            call.respond(meals.toDto())
        }
    }

    route("meal") {

        get("/{id}") {
            val id = call.parameters.extractString("id") ?: throw NotFoundException("ID not provided")
            val meal = mealUseCasesContainer.getMealByIdUseCase(id)
            call.respond(meal.toDto())
        }

        post {
            val meal = call.receive<MealDto>()
            val isAdded = mealUseCasesContainer.addMealUseCase(meal.toEntity())
            call.respond(HttpStatusCode.Created, isAdded)
        }

        delete("/{id}") {
            val id = call.parameters.extractString("id") ?: throw NotFoundException("ID not provided")
            val isDeleted = mealUseCasesContainer.deleteMealUseCase(id)
            call.respond(HttpStatusCode.OK, isDeleted)
        }

        put {
            val meal = call.receive<MealDto>()
            val isUpdated = mealUseCasesContainer.updateMealUseCase(meal.toEntity())
            call.respond(HttpStatusCode.OK, isUpdated)
        }

        post("/{mealId}/cuisine/{cuisineId}") {
            val mealId = call.parameters.extractString("mealId") ?: throw NotFoundException("ID not provided")
            val cuisineId = call.parameters.extractString("cuisineId") ?: throw NotFoundException("ID not provided")
            val isAdded = mealUseCasesContainer.addCuisineToMealUseCase(mealId, cuisineId)
            call.respond(HttpStatusCode.OK, isAdded)
        }

        delete("/{mealId}/cuisine/{cuisineId}") {
            val mealId = call.parameters.extractString("mealId") ?: throw NotFoundException("ID not provided")
            val cuisineId = call.parameters.extractString("cuisineId") ?: throw NotFoundException("ID not provided")
            val isDeleted = mealUseCasesContainer.deleteCuisineFromMealUseCase(mealId, cuisineId)
            call.respond(HttpStatusCode.OK, isDeleted)
        }

        get("/{mealId}/cuisine") {
            val mealId = call.parameters.extractString("mealId") ?: throw NotFoundException("ID not provided")
            val cuisines = mealUseCasesContainer.getMealCuisinesUseCase(mealId)
            call.respond(cuisines.toDto())
        }

    }

}