package org.thechance.service_taxi.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.async
import org.koin.ktor.ext.inject
import org.thechance.service_taxi.api.dto.BasePaginationResponse
import org.thechance.service_taxi.api.dto.trip.TripDto
import org.thechance.service_taxi.api.dto.trip.WebSocketTrip
import org.thechance.service_taxi.api.dto.trip.toDto
import org.thechance.service_taxi.api.dto.trip.toEntity
import org.thechance.service_taxi.api.util.SocketHandler
import org.thechance.service_taxi.domain.exceptions.MissingParameterException
import org.thechance.service_taxi.domain.usecase.IClientTripsManagementUseCase
import org.thechance.service_taxi.domain.usecase.IDriverTripsManagementUseCase
import org.thechance.service_taxi.domain.usecase.IManageTripsUseCase

fun Route.tripRoutes() {
    val manageTripsUseCase: IManageTripsUseCase by inject()
    val driverTripsManagementUseCase: IDriverTripsManagementUseCase by inject()
    val clientTripsManagementUseCase: IClientTripsManagementUseCase by inject()
    val socketHandler: SocketHandler by inject()

    route("/trip") {
        get {
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = manageTripsUseCase.getTrips(page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/{tripId}") {
            val id = call.parameters["tripId"] ?: throw MissingParameterException()
            val result = manageTripsUseCase.getTripById(id)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/user/{orderId}") {
            val orderId = call.parameters["orderId"] ?: throw MissingParameterException()
            val result = manageTripsUseCase.getTripByOrderId(orderId)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/driver/{driverId}") {
            val id = call.parameters["driverId"] ?: throw MissingParameterException()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = driverTripsManagementUseCase.getTripsByDriverId(id, page, limit).toDto()
            val total = driverTripsManagementUseCase.getNumberOfTripsByDriverId(id)
            call.respond(HttpStatusCode.OK, BasePaginationResponse(result, page, total))
        }

        get("/actives/{userId}") {
            val userId = call.parameters["userId"] ?: throw MissingParameterException()
            val trips = driverTripsManagementUseCase.getActiveTripsByUserId(userId).toDto()
            call.respond(HttpStatusCode.OK, trips)
        }

        get("/client/{clientId}") {
            val id = call.parameters["clientId"] ?: throw MissingParameterException()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val result = clientTripsManagementUseCase.getTripsByClientId(id, page, limit).toDto()
            val total = clientTripsManagementUseCase.getNumberOfTripsByClientId(id)
            call.respond(HttpStatusCode.OK, BasePaginationResponse(result, page, total))
        }

        post {
            val tripDto = async { call.receive<TripDto>() }.await()
            val result = async { clientTripsManagementUseCase.createTrip(tripDto.toEntity()) }.await().toDto()
            socketHandler.broadcastChannel.emit(result)
            call.respond(HttpStatusCode.Created, result)
        }

        webSocket("/taxi/{driverId}") {
            val taxiDriverId = call.parameters["driverId"]?.trim().orEmpty()
            socketHandler.trips[taxiDriverId] = WebSocketTrip(session = this, isATaxiTrip = true)
            socketHandler.collectTrips(taxiDriverId)
        }

        webSocket("/delivery/{deliveryId}") {
            val deliveryId = call.parameters["deliveryId"]?.trim().orEmpty()
            socketHandler.trips[deliveryId] = WebSocketTrip(session = this, isATaxiTrip = false)
            socketHandler.collectTrips(deliveryId)
        }

        webSocket("/track/{tripId}") {
            val tripId = call.parameters["tripId"]?.trim().orEmpty()
            socketHandler.trips[tripId] = WebSocketTrip(session = this)
            socketHandler.collectTripStatus(tripId)
        }

        put("/update/{tripId}") {
            val parameters = call.receiveParameters()
            val tripId = call.parameters["tripId"] ?: throw MissingParameterException()
            val driverId = parameters["driverId"] ?: throw MissingParameterException()
            val taxiId = parameters["taxiId"] ?: throw MissingParameterException()
            val result = async {
                driverTripsManagementUseCase.updateTrip(
                    driverId = driverId,
                    taxiId = taxiId,
                    tripId = tripId
                )
            }.await().toDto()
            socketHandler.trips[tripId]?.trip?.emit(result)
            socketHandler.endSession(driverId)
            call.respond(HttpStatusCode.OK, result)
        }

        put("/{tripId}/rate") {
            val tripId = call.parameters["tripId"] ?: throw MissingParameterException()
            val rate = call.parameters["rate"]?.toDouble() ?: throw MissingParameterException()
            val result = clientTripsManagementUseCase.rateTrip(tripId, rate)
            call.respond(HttpStatusCode.OK, result.toDto())
        }
    }
}