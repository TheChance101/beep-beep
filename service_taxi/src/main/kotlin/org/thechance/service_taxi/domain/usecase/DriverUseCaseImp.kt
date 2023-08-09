package org.thechance.service_taxi.domain.usecase

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.entity.TripUpdateRequest
import org.thechance.service_taxi.domain.gateway.DataBaseGateway

@Single
class DriverUseCaseImp(
    private val dataBaseGateway: DataBaseGateway,
) : DriverUseCase {
    override suspend fun approveTrip(driverId: String, taxiId: String, tripId: String) {
        dataBaseGateway.updateTrip(
            TripUpdateRequest(
                id = tripId,
                taxiId = taxiId,
                driverId = driverId,
                startDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
        )
    }

    override suspend fun finishTrip(driverId: String, tripId: String) {
        dataBaseGateway.updateTrip(
            TripUpdateRequest(
                tripId,
                endDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
        )
    }

    override suspend fun getTripsByDriverId(
        driverId: String,
        page: Int,
        limit: Int
    ): List<Trip> {
        return dataBaseGateway.getDriverTripsHistory(driverId, page, limit)
    }
}