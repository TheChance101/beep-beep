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

    val useCase: CategoryUseCasesContainer by inject()

    route("/category") {

        get {
            val categories = useCase.getCategories().toDto()
            call.respond(HttpStatusCode.OK, categories)
        }

        get("/{id}") {
            val categoryId = call.parameters["id"] ?: ""
            val category = useCase.getCategoryDetails(categoryId).toDto()
            call.respond(HttpStatusCode.OK, category)
        }

        post {
            val category = call.receive<CategoryDto>()
            val result = useCase.addCategory(category.toEntity())
            call.respond(HttpStatusCode.Created, result)
        }

        put {
            val category = call.receive<CategoryDto>()
            val result = useCase.updateCategory(category.toEntity())
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            val categoryId = call.parameters["id"] ?: ""
            val result = useCase.deleteCategory(categoryId)
            call.respond(HttpStatusCode.OK, result)
        }

    }
}