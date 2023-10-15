package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.BasePaginationResponseDto
import org.thechance.service_restaurant.api.models.CategoryDto
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.api.utils.extractInt
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.domain.usecase.IDiscoverRestaurantUseCase
import org.thechance.service_restaurant.domain.usecase.IManageCategoryUseCase
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND


fun Route.categoryRoutes() {

    val manageCategory: IManageCategoryUseCase by inject()
    val discoverRestaurant: IDiscoverRestaurantUseCase by inject()

    get("/categories") {
        val page = call.parameters.extractInt("page") ?: 1
        val limit = call.parameters.extractInt("limit") ?: 10
        val categories = manageCategory.getCategories(page, limit).toDto()
        val total = manageCategory.getTotalNumberOfCategories()
        call.respond(HttpStatusCode.OK, BasePaginationResponseDto(categories, page, total))
    }

    route("/category") {

        get("/{id}/restaurants") {
            val categoryId = call.parameters.extractString("id") ?:  throw MultiErrorException(listOf(NOT_FOUND))
            val category = discoverRestaurant.getRestaurantsInCategory(categoryId).toDto()
            call.respond(HttpStatusCode.OK, category)
        }

        post {
            val category = call.receive<CategoryDto>()
            val result = manageCategory.createCategory(category.toEntity())
            call.respond(HttpStatusCode.Created, result.toDto())
        }

        put {
            val category = call.receive<CategoryDto>()
            val result = manageCategory.updateCategory(category.toEntity())
            call.respond(HttpStatusCode.OK, result.toDto())
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