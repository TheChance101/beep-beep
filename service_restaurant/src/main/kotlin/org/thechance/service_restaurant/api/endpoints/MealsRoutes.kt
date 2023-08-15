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
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.domain.usecase.IDiscoverRestaurantUseCase
import org.thechance.service_restaurant.domain.usecase.IManageMealUseCase

fun Route.mealRoutes() {
    val manageMealUseCase: IManageMealUseCase by inject()
    val discoverRestaurant: IDiscoverRestaurantUseCase by inject()

    route("meal") {

        get("/{id}") {
            val id = call.parameters.extractString("id") ?: ""
            val meal = discoverRestaurant.getMealDetails(id)
            call.respond(meal.toDto())
        }

        put {
            val meal = call.receive<MealWithCuisineDto>()
            val result = manageMealUseCase.updateMealToRestaurant(meal.toEntity())
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        post {
            val meal = call.receive<MealWithCuisineDto>()
            val result = manageMealUseCase.addMealToRestaurant(meal.toEntity())
            call.respond(HttpStatusCode.Created, result.toDto())
        }

        delete("/{id}") {
            val id = call.parameters.extractString("id") ?: ""
            val isDeleted = manageMealUseCase.deleteMealFromRestaurant(id)
            if (isDeleted) call.respond(HttpStatusCode.OK, "Meal deleted Successfully")
        }
    }
}