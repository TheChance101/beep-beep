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
import org.thechance.service_restaurant.api.usecases.MealUseCasesContainer
import org.thechance.service_restaurant.api.utils.extractInt
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.data.collection.MealCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity

fun Route.mealRoutes() {

    val mealUseCasesContainer: MealUseCasesContainer by inject()

    route("meals") {
        get("/{page}") {
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
            if (isAdded)
                call.respond(HttpStatusCode.Created, "Meal added Successfully")
            else
                call.respond(HttpStatusCode.BadRequest, "Fail to add meal")
        }

        delete("/{id}") {
            val id = call.parameters.extractString("id") ?: throw NotFoundException("ID not provided")
            val isDeleted = mealUseCasesContainer.deleteMealUseCase(id)
            if (isDeleted)
                call.respond(HttpStatusCode.OK, "Meal deleted Successfully")
            else
                call.respond(HttpStatusCode.NotFound, "Not Found")
        }

        put("/{id}") {
            val id = call.parameters.extractString("id") ?: throw NotFoundException("ID not provided")
            val meal = call.receive<MealDto>()
            val isUpdated = mealUseCasesContainer.updateMealUseCase(id, meal.toEntity())
            if (isUpdated)
                call.respond(HttpStatusCode.OK, "updated successfully")
            else
                call.respond(HttpStatusCode.NotFound, "Failed to update meal")
        }

    }

}