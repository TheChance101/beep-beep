package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.CategoryDto
import org.thechance.service_restaurant.api.usecases.CategoryUseCasesContainer
import org.thechance.service_restaurant.entity.toDto


fun Route.categoryRoutes() {

    val categoryUseCases: CategoryUseCasesContainer by inject()

    route("/category") {

        get {
            val categories = categoryUseCases.getCategories().toDto()
            call.respond(HttpStatusCode.OK, categories)
        }

        get("/{id}") {
            val categoryId = call.parameters["id"] ?: ""
            val category = categoryUseCases.getCategoryDetails(categoryId).toDto()
            call.respond(HttpStatusCode.OK, category)
        }

        get("/{id}/restaurants") {
            val categoryId = call.parameters["id"] ?: ""
            val category = categoryUseCases.getRestaurantsInCategory(categoryId).toDto()
            call.respond(HttpStatusCode.OK, category)
        }

        post {
            val category = call.receive<CategoryDto>()
            val result = categoryUseCases.addCategory(category.toEntity())
            call.respond(HttpStatusCode.Created, result)
        }

        post("/{id}/addRestaurant") {
            val categoryId = call.parameters["id"] ?: ""
            val restaurantIds = call.receive<List<String>>()
            val result = categoryUseCases.addRestaurantsToCategory(categoryId, restaurantIds)
            call.respond(HttpStatusCode.OK, result)
        }

        put {
            val category = call.receive<CategoryDto>()
            val result = categoryUseCases.updateCategory(category.toEntity())
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            val categoryId = call.parameters["id"] ?: ""
            val result = categoryUseCases.deleteCategory(categoryId)
            call.respond(HttpStatusCode.OK, result)
        }

    }
}