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
import org.thechance.service_restaurant.domain.usecase.meal.ManageCuisinesInMealUseCase
import org.thechance.service_restaurant.domain.usecase.meal.ManageMealUseCase

fun Route.mealRoutes() {

    val manageMeal: ManageMealUseCase by inject()
    val manageCuisinesInMeal: ManageCuisinesInMealUseCase by inject()

    route("meals") {
        get {
            val page = call.parameters.extractInt("page") ?: 1
            val limit = call.parameters.extractInt("limit") ?: 10
            val meals = manageMeal.getMeals(page, limit)
            call.respond(meals.toDto())
        }
    }

    route("meal") {

        get("/{id}") {
            val id = call.parameters.extractString("id") ?: ""
            val meal = manageMeal.getMealById(id)
            call.respond(meal.toDto())
        }

        get("/{mealId}/cuisine") {
            val mealId = call.parameters.extractString("mealId") ?: ""
            val cuisines = manageCuisinesInMeal.getMealCuisines(mealId)
            call.respond(cuisines.toDto())
        }

        post {
            val meal = call.receive<MealDto>()
            val isAdded = manageMeal.addMeal(meal.toEntity())
            if (isAdded) call.respond(HttpStatusCode.Created, "Meal added Successfully")
        }

        post("/{mealId}/cuisine") {
            val mealId = call.parameters.extractString("mealId") ?: ""
            val cuisineIds = call.receive<List<String>>()
            val isAdded = manageCuisinesInMeal.addCuisinesToMeal(mealId, cuisineIds)
            if (isAdded) call.respond(HttpStatusCode.OK, "Cuisine added Successfully")
        }

        put {
            val meal = call.receive<MealDto>()
            val isUpdated = manageMeal.updateMeal(meal.toEntity())
            if (isUpdated) call.respond(HttpStatusCode.OK, "updated successfully")
        }

        delete("/{id}") {
            val id = call.parameters.extractString("id") ?: ""
            val isDeleted = manageMeal.deleteMeal(id)
            if (isDeleted) call.respond(HttpStatusCode.OK, "Meal deleted Successfully")
        }

        delete("/{mealId}/cuisine/{cuisineId}") {
            val mealId = call.parameters.extractString("mealId") ?: ""
            val cuisineId = call.parameters.extractString("cuisineId") ?: throw NotFoundException("ID not provided")
            val isDeleted = manageCuisinesInMeal.deleteCuisineFromMeal(mealId, cuisineId)
            if (isDeleted) call.respond(HttpStatusCode.OK, "Cuisine deleted Successfully")
        }

    }

}