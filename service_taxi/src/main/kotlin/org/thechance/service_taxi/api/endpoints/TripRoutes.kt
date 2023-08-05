package org.thechance.service_taxi.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_taxi.api.models.trip.TripDto
import org.thechance.service_taxi.api.models.trip.toDto
import org.thechance.service_taxi.api.models.trip.toEntity
import org.thechance.service_taxi.domain.usecase.ClientUseCase
import org.thechance.service_taxi.domain.usecase.TaxiManagementsUseCase
import org.thechance.service_taxi.domain.usecase.TripManagementsUseCase

fun Route.tripRoutes() {
    val tripManagement: TripManagementsUseCase by inject()
    val taxiManagements: TaxiManagementsUseCase by inject()
    val client: ClientUseCase by inject()


    route("/trip") {
        get {
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = taxiManagements.getTrips(page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/{tripId}") {
            val id = call.parameters["tripId"]?.trim().orEmpty()
            val result = client.getTripById(id)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/driver/{driverId}") {
            val id = call.parameters["driverId"]?.trim().orEmpty()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = taxiManagements.getDriverTripsHistory(id, page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/client/{clientId}") {
            val id = call.parameters["clientId"]?.trim().orEmpty()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = taxiManagements.getClientTripsHistory(id, page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        post {
            val tripDto = call.receive<TripDto>()
            val result = client.addTrip(tripDto.toEntity())
            if (result) {
                call.respond(HttpStatusCode.Created, "added")
            } else {
                call.respond(HttpStatusCode.BadRequest, "taxi not added")
            }
        }

        put("/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            val tripDto = call.receive<TripDto>()
            val result = tripManagement.updateTripState(tripDto.toEntity().copy(id = tripId))
            if (result) {
                call.respond(HttpStatusCode.OK, "updated")
            } else {
                call.respond(HttpStatusCode.NotFound, "taxi not updated")
            }
        }

        delete("/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            val result = taxiManagements.deleteTrip(tripId)
            if (result) {
                call.respond(HttpStatusCode.OK, "deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "taxi not deleted")
            }
        }
    }
}