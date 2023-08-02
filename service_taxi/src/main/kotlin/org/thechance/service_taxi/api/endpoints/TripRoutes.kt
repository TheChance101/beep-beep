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
import org.thechance.service_taxi.api.models.trip.TripDto
import org.thechance.service_taxi.api.models.trip.toDto
import org.thechance.service_taxi.api.models.trip.toEntity
import org.thechance.service_taxi.api.usecase.trip.TripUseCasesContainer

fun Route.tripRoutes() {
    val tripUseCasesContainer: TripUseCasesContainer by inject()

    route("/trip") {
        get {
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = tripUseCasesContainer.getAllTrips(page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/{tripId}") {
            val id = call.parameters["tripId"]?.trim().orEmpty()
            val result = tripUseCasesContainer.getTripById(id)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/driver/{driverId}") {
            val id = call.parameters["driverId"]?.trim().orEmpty()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = tripUseCasesContainer.getDriverTripsHistory(id, page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/client/{clientId}") {
            val id = call.parameters["clientId"]?.trim().orEmpty()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = tripUseCasesContainer.getClientTripsHistory(id, page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        post {
            val tripDto = call.receive<TripDto>()
            val result = tripUseCasesContainer.addTrip(tripDto.toEntity())
            if (result) {
                call.respond(HttpStatusCode.Created, "added")
            } else {
                call.respond(HttpStatusCode.BadRequest, "taxi not added")
            }
        }

        put("/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            val tripDto = call.receive<TripDto>()
            val result = tripUseCasesContainer.updateTrip(tripDto.toEntity().copy(id = tripId))
            if (result) {
                call.respond(HttpStatusCode.OK, "updated")
            } else {
                call.respond(HttpStatusCode.NotFound, "taxi not updated")
            }
        }

        delete("/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            val result = tripUseCasesContainer.deleteTrip(tripId)
            if (result) {
                call.respond(HttpStatusCode.OK, "deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "taxi not deleted")
            }
        }
    }
}