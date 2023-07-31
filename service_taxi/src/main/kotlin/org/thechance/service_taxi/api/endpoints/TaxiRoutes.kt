package org.thechance.service_taxi.api.endpoints

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
import org.thechance.service_taxi.api.models.TaxiDto
import org.thechance.service_taxi.api.models.toDto
import org.thechance.service_taxi.api.models.toTaxi
import org.thechance.service_taxi.domain.usecase.TaxiUseCasesContainer

fun Route.taxiRoutes() {
    val taxiUseCasesContainer: TaxiUseCasesContainer by inject()

    route("/taxi") {
        get {
            val result = taxiUseCasesContainer.getAllTaxesUseCase()
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/{taxiId}") {
            val id = call.parameters["taxiId"]?.trim().orEmpty()
            val result = taxiUseCasesContainer.getTaxiByIdUseCase(id)

            result?.let { call.respond(HttpStatusCode.OK, result.toDto()) }
                ?: call.respond(HttpStatusCode.NotFound, "taxi not found")
        }

        post {
            val taxi = call.receive<TaxiDto>()
            val result = taxiUseCasesContainer.addTaxiUseCase(taxi.toTaxi())
            if (result) {
                call.respond(HttpStatusCode.OK, "added")
            } else {
                call.respond(HttpStatusCode.BadGateway, "taxi not added")
            }
        }

        put {
            val taxiId = call.parameters["taxiId"]?.trim().orEmpty()
            val taxi = call.receive<TaxiDto>()
            val result = taxiUseCasesContainer.updateTaxiByIdUseCase(taxiId, taxi.toTaxi())
            if (result) {
                call.respond(HttpStatusCode.OK, "updated")
            } else {
                call.respond(HttpStatusCode.BadGateway, "taxi not updated")
            }
        }

        delete {
            val taxiId = call.parameters["taxiId"]?.trim().orEmpty()
            val result = taxiUseCasesContainer.deleteTaxiUseCase(taxiId)
            if (result) {
                call.respond(HttpStatusCode.OK, "deleted")
            } else {
                call.respond(HttpStatusCode.BadGateway, "taxi not deleted")
            }
        }
    }
}