package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.MealWithCuisineDto
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.api.models.mappers.toMealDto
import org.thechance.service_restaurant.api.utils.extractInt
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.domain.usecase.AdministratorUseCase
import org.thechance.service_restaurant.domain.usecase.ClientUseCase
import org.thechance.service_restaurant.domain.usecase.ManageRestaurantUseCase

fun Route.mealRoutes() {
    val client: ClientUseCase by inject()
    val admin: AdministratorUseCase by inject()
    val manageRestaurant: ManageRestaurantUseCase by inject()

    get("meals") {
        val page = call.parameters.extractInt("page") ?: 1
        val limit = call.parameters.extractInt("limit") ?: 10
        val meals = admin.getAllMeals(page, limit)
        call.respond(meals.toMealDto())
    }

    route("meal") {

        get("/{id}") {
            val id = call.parameters.extractString("id") ?: ""
            val meal = client.getMealDetails(id)
            call.respond(meal.toDto())
        }

        put {
            val meal = call.receive<MealWithCuisineDto>()
            val isUpdated = manageRestaurant.updateMealToRestaurant(meal.toEntity())
            if (isUpdated) call.respond(HttpStatusCode.OK, "updated successfully")
        }

        post {
            val meal = call.receive<MealWithCuisineDto>()
            val result = manageRestaurant.addMealToRestaurant(meal.toEntity())
            call.respond(HttpStatusCode.Created, result)
        }

        delete("/{id}") {
            val id = call.parameters.extractString("id") ?: ""
            val isDeleted = manageRestaurant.deleteMealFromRestaurant(id)
            if (isDeleted) call.respond(HttpStatusCode.OK, "Meal deleted Successfully")
        }
    }
}