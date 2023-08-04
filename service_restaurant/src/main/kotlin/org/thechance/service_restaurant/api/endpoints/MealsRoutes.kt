package org.thechance.service_restaurant.api.endpoints

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.domain.usecase.ClientUseCase

fun Route.mealRoutes() {

    val client: ClientUseCase by inject()

//    route("meals") {
//        get {
//            val page = call.parameters.extractInt("page") ?: 1
//            val limit = call.parameters.extractInt("limit") ?: 10
//            val meals = manageMeal.getMeals(page, limit)
//            call.respond(meals.toDto())
//        }
//    }

    route("meal") {

        get("/{id}") {
            val id = call.parameters.extractString("id") ?: ""
            val meal = client.getMealDetails(id)
            call.respond(meal.toDto())
        }

//        get("/{mealId}/cuisine") {
//            val mealId = call.parameters.extractString("mealId") ?: ""
//            val cuisines = manageCuisinesInMeal.getMealCuisines(mealId)
//            call.respond(cuisines.toDto())
//        }

//        post {
//            val meal = call.receive<MealDto>()
//            val isAdded = manageMeal.addMeal(meal.toEntity())
//            if (isAdded) call.respond(HttpStatusCode.Created, "Meal added Successfully")
//        }

//        put {
//            val meal = call.receive<MealDto>()
//            val isUpdated = manageMeal.updateMeal(meal.toEntity())
//            if (isUpdated) call.respond(HttpStatusCode.OK, "updated successfully")
//        }

//        delete("/{id}") {
//            val id = call.parameters.extractString("id") ?: ""
//            val isDeleted = manageMeal.deleteMeal(id)
//            if (isDeleted) call.respond(HttpStatusCode.OK, "Meal deleted Successfully")
//        }
//
//        delete("/{mealId}/cuisine/{cuisineId}") {
//            val mealId = call.parameters.extractString("mealId") ?: ""
//            val cuisineId = call.parameters.extractString("cuisineId") ?: throw NotFoundException("ID not provided")
//            val isDeleted = manageCuisinesInMeal.deleteCuisineFromMeal(mealId, cuisineId)
//            if (isDeleted) call.respond(HttpStatusCode.OK, "Cuisine deleted Successfully")
//        }

    }

}