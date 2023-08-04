package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.utils.extractInt
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.domain.usecase.ClientUseCase


fun Route.categoryRoutes() {

    val client: ClientUseCase by inject()

    route("/categories") {
        get {
            val page = call.parameters.extractInt("page") ?: 1
            val limit = call.parameters.extractInt("limit") ?: 10
            val categories = client.getCategories(page, limit).toDto()
            call.respond(HttpStatusCode.OK, categories)
        }
    }

    route("/category") {

//        get("/{id}") {
//            val categoryId = call.parameters.extractString("id") ?: ""
//            val category = manageCategory.getCategoryDetails(categoryId).toDto()
//            call.respond(HttpStatusCode.OK, category)
//        }

        get("/{id}/restaurants") {
            val categoryId = call.parameters.extractString("id") ?: ""
            val category = client.getRestaurantsInCategory(categoryId).toDto()
            call.respond(HttpStatusCode.OK, category)
        }

//        post {
//            val category = call.receive<CategoryDto>()
//            val result = manageCategory.createCategory(category.toEntity())
//            call.respond(HttpStatusCode.Created, result)
//        }

//        post("/{id}/addRestaurant") {
//            val categoryId = call.parameters.extractString("id") ?: ""
//            val restaurantIds = call.receive<List<String>>()
//            val result = manageRestaurantInCategory.addRestaurantsToCategory(categoryId, restaurantIds)
//            call.respond(HttpStatusCode.OK, result)
//        }
//
//        put {
//            val category = call.receive<CategoryDto>()
//            val result = manageCategory.updateCategory(category.toEntity())
//            call.respond(HttpStatusCode.OK, result)
//        }
//
//        delete("/{id}") {
//            val categoryId = call.parameters.extractString("id") ?: ""
//            val result = manageCategory.deleteCategory(categoryId)
//            call.respond(HttpStatusCode.OK, result)
//        }
//
//        delete("/{id}/restaurants") {
//            val categoryId = call.parameters.extractString("id") ?: ""
//            val restaurantIds = call.receive<List<String>>()
//            val result = manageRestaurantInCategory.deleteRestaurantsInCategory(categoryId, restaurantIds)
//            call.respond(HttpStatusCode.OK, result)
//        }

    }
}