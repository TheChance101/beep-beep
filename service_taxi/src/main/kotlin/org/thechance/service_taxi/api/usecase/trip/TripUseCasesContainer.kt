package org.thechance.service_taxi.api.usecase.trip

import org.koin.core.annotation.Single

@Single
data class TripUseCasesContainer(
    val addTrip: AddTripUseCase,
    val deleteTrip: DeleteTripUseCase,
    val getAllTrips: GetAllTripsUseCase,
    val getTripById: GetTripByIdUseCase,
    val updateTrip: UpdateTripUseCase,
    val getClientTripsHistory: GetClientTripsHistoryUseCase,
    val getDriverTripsHistory: GetDriverTripsHistoryUseCase,
)
