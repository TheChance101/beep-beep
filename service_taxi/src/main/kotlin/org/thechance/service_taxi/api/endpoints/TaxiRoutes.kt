package org.thechance.service_taxi.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_taxi.api.models.taxi.TaxiDto
import org.thechance.service_taxi.api.models.taxi.toDto
import org.thechance.service_taxi.api.models.taxi.toEntity
import org.thechance.service_taxi.domain.usecase.ClientUseCase
import org.thechance.service_taxi.domain.usecase.TaxiManagementsUseCase

fun Route.taxiRoutes() {
    val taxiManagement: TaxiManagementsUseCase by inject()
    val client: ClientUseCase by inject()

    route("/taxi") {
        get {
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = taxiManagement.getAllTaxi(page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/{taxiId}") {
            val id = call.parameters["taxiId"]?.trim().orEmpty()
            val result = client.getTaxi(id)

            result?.let { call.respond(HttpStatusCode.OK, result.toDto()) }
                ?: call.respond(HttpStatusCode.NotFound, "taxi not found")
        }

        post {
            val taxi = call.receive<TaxiDto>()
            val result = taxiManagement.addTaxi(taxi.toEntity())
            if (result) {
                call.respond(HttpStatusCode.OK, "added")
            } else {
                call.respond(HttpStatusCode.NotFound, "taxi not added")
            }
        }

        put("/{taxiId}") {
            val taxiId = call.parameters["taxiId"]?.trim().orEmpty()
            val taxi = call.receive<TaxiDto>()
            val result =
                taxiManagement.updateTaxi(taxi.toEntity().copy(id = taxiId))
            if (result) {
                call.respond(HttpStatusCode.OK, "updated")
            } else {
                call.respond(HttpStatusCode.NotFound, "taxi not updated")
            }
        }

        delete("/{taxiId}") {
            val taxiId = call.parameters["taxiId"]?.trim().orEmpty()
            val result = taxiManagement.deleteTaxi(taxiId)
            if (result) {
                call.respond(HttpStatusCode.OK, "deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "taxi not deleted")
            }
        }
    }
}