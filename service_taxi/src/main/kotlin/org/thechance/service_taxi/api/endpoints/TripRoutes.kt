package org.thechance.service_taxi.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject
import org.thechance.service_taxi.api.dto.BasePaginationResponse
import org.thechance.service_taxi.api.dto.trip.TripDto
import org.thechance.service_taxi.api.dto.trip.toDto
import org.thechance.service_taxi.api.dto.trip.toEntity
import org.thechance.service_taxi.domain.exceptions.MissingParameterException
import org.thechance.service_taxi.domain.usecase.IClientTripsManagementUseCase
import org.thechance.service_taxi.domain.usecase.IDriverTripsManagementUseCase
import org.thechance.service_taxi.domain.usecase.IManageTripsUseCase

fun Route.tripRoutes() {
    val manageTripsUseCase: IManageTripsUseCase by inject()
    val driverTripsManagementUseCase: IDriverTripsManagementUseCase by inject()
    val clientTripsManagementUseCase: IClientTripsManagementUseCase by inject()

    route("/trip") {
        get {
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = manageTripsUseCase.getTrips(page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/{tripId}") {
            val id = call.parameters["tripId"] ?: throw MissingParameterException
            val result = manageTripsUseCase.getTripById(id)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/driver/{driverId}") {
            val id = call.parameters["driverId"] ?: throw MissingParameterException
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = driverTripsManagementUseCase.getTripsByDriverId(id, page, limit).toDto()
            val total = driverTripsManagementUseCase.getNumberOfTripsByDriverId(id)
            call.respond(HttpStatusCode.OK, BasePaginationResponse(result, total))
        }

        get("/client/{clientId}") {
            val id = call.parameters["clientId"] ?: throw MissingParameterException
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = clientTripsManagementUseCase.getTripsByClientId(id, page, limit).toDto()
            val total = clientTripsManagementUseCase.getNumberOfTripsByClientId(id)
            call.respond(HttpStatusCode.OK, BasePaginationResponse(result, total))
        }

        post {
            val tripDto = call.receive<TripDto>()
            val result = clientTripsManagementUseCase.createTrip(tripDto.toEntity())
            call.respond(HttpStatusCode.Created, result.toDto())
        }

        put("/{tripId}/rate") {
            val tripId = call.parameters["tripId"] ?: throw MissingParameterException
            val rate = call.parameters["rate"]?.toDouble() ?: throw MissingParameterException
            val result = clientTripsManagementUseCase.rateTrip(tripId, rate)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        put("/approve") {
            val parameters = call.receiveParameters()
            val tripId = parameters["tripId"] ?: throw MissingParameterException
            val driverId = parameters["driverId"] ?: throw MissingParameterException
            val taxiId = parameters["taxiId"] ?: throw MissingParameterException
            val result = driverTripsManagementUseCase.approveTrip(driverId, taxiId, tripId)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        put("/finish") {
            val parameters = call.receiveParameters()
            val tripId = parameters["tripId"] ?: throw MissingParameterException
            val driverId = parameters["driverId"] ?: throw MissingParameterException
            val result = driverTripsManagementUseCase.finishTrip(driverId, tripId)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        delete("/{tripId}") {
            val tripId = call.parameters["tripId"] ?: throw MissingParameterException
            val result = manageTripsUseCase.deleteTrip(tripId)
            call.respond(HttpStatusCode.OK, result.toDto())
        }
    }
}