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
import org.thechance.service_restaurant.domain.usecase.meal.MealUseCasesContainer

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
            val id = call.parameters.extractString("id") ?: ""
            val meal = mealUseCasesContainer.getMealByIdUseCase(id)
            call.respond(meal.toDto())
        }

        get("/{mealId}/cuisine") {
            val mealId = call.parameters.extractString("mealId") ?: ""
            val cuisines = mealUseCasesContainer.getMealCuisinesUseCase(mealId)
            call.respond(cuisines.toDto())
        }

        post {
            val meal = call.receive<MealDto>()
            val isAdded = mealUseCasesContainer.addMealUseCase(meal.toEntity())
            if (isAdded) call.respond(HttpStatusCode.Created, "Meal added Successfully")
        }

        post("/{mealId}/cuisine") {
            val mealId = call.parameters.extractString("mealId") ?: ""
            val cuisineIds = call.receive<List<String>>()
            val isAdded = mealUseCasesContainer.addCuisinesToMealUseCase(mealId, cuisineIds)
            if (isAdded) call.respond(HttpStatusCode.OK, "Cuisine added Successfully")
        }

        put {
            val meal = call.receive<MealDto>()
            val isUpdated = mealUseCasesContainer.updateMealUseCase(meal.toEntity())
            if (isUpdated) call.respond(HttpStatusCode.OK, "updated successfully")
        }

        delete("/{id}") {
            val id = call.parameters.extractString("id") ?: ""
            val isDeleted = mealUseCasesContainer.deleteMealUseCase(id)
            if (isDeleted) call.respond(HttpStatusCode.OK, "Meal deleted Successfully")
        }

        delete("/{mealId}/cuisine/{cuisineId}") {
            val mealId = call.parameters.extractString("mealId") ?: ""
            val cuisineId = call.parameters.extractString("cuisineId") ?: throw NotFoundException("ID not provided")
            val isDeleted = mealUseCasesContainer.deleteCuisineFromMealUseCase(mealId, cuisineId)
            if (isDeleted) call.respond(HttpStatusCode.OK, "Cuisine deleted Successfully")
        }

    }

}