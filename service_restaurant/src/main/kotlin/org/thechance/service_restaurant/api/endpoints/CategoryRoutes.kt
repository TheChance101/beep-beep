package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.CategoryDto
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.api.utils.extractInt
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.domain.usecase.IDiscoverRestaurantUseCase
import org.thechance.service_restaurant.domain.usecase.IManageCategoryUseCase
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException


fun Route.categoryRoutes() {

    val manageCategory: IManageCategoryUseCase by inject()
    val discoverRestaurant: IDiscoverRestaurantUseCase by inject()

    get("/categories") {
        val page = call.parameters.extractInt("page") ?: 1
        val limit = call.parameters.extractInt("limit") ?: 10
        val categories = manageCategory.getCategories(page, limit).toDto()
        call.respond(HttpStatusCode.OK, categories)
    }

    route("/category") {

        get("/{id}/restaurants") {
            val categoryId = call.parameters.extractString("id") ?: ""
            val category = discoverRestaurant.getRestaurantsInCategory(categoryId).toDto()
            call.respond(HttpStatusCode.OK, category)
        }

        post {
            val category = call.receive<CategoryDto>()
            val result = manageCategory.createCategory(category.toEntity())
            call.respond(HttpStatusCode.Created, result)
        }

        put {
            val category = call.receive<CategoryDto>()
            val result = manageCategory.updateCategory(category.toEntity())
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            val categoryId =
                call.parameters.extractString("id") ?:
                throw MultiErrorException(listOf(INVALID_REQUEST_PARAMETER))
            val result = manageCategory.deleteCategory(categoryId)
            call.respond(HttpStatusCode.OK, result)
        }

    }
}