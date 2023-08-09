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
import org.thechance.service_taxi.api.models.taxi.TaxiDto
import org.thechance.service_taxi.api.models.taxi.toDto
import org.thechance.service_taxi.api.models.taxi.toEntity
import org.thechance.service_taxi.api.models.taxi.toUpdateRequest
import org.thechance.service_taxi.domain.usecase.IManageTaxiUseCase
import org.thechance.service_taxi.domain.util.MissingParameterException

fun Route.taxiRoutes() {
    val manageTaxiUseCase: IManageTaxiUseCase by inject()

    route("/taxi") {
        get {
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = manageTaxiUseCase.getAllTaxi(page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/{taxiId}") {
            val id = call.parameters["taxiId"] ?: throw MissingParameterException
            val result = manageTaxiUseCase.getTaxi(id)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        post {
            val taxi = call.receive<TaxiDto>()
            manageTaxiUseCase.createTaxi(taxi.toEntity())
            call.respond(HttpStatusCode.OK, "added")
        }

        put("/{taxiId}") {
            val taxiId = call.parameters["taxiId"] ?: throw MissingParameterException
            val taxi = call.receive<TaxiDto>()
            manageTaxiUseCase.updateTaxi(taxi.toUpdateRequest().copy(id = taxiId))
            call.respond(HttpStatusCode.OK, "updated")
        }

        delete("/{taxiId}") {
            val taxiId = call.parameters["taxiId"] ?: throw MissingParameterException
            manageTaxiUseCase.deleteTaxi(taxiId)
            call.respond(HttpStatusCode.OK, "deleted")
        }
    }
}