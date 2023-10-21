package org.thechance.service_taxi.domain.entity

import kotlinx.datetime.LocalDateTime

data class Trip(
    val id: String,
    val taxiId: String? = null,
    val driverId: String? = null,
    val taxiPlateNumber: String? = null,
    val driverName: String? = null,
    val taxiColor: Color = Color.WHITE,
    val clientId: String,
    val restaurantId: String? = null,
    val startPoint: Location,
    val destination: Location?,
    val startPointAddress: String,
    val destinationAddress: String,
    val rate: Double? = null,
    val price: Double,
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
    val isATaxiTrip: Boolean,
    val tripStatus: Status
) {
    enum class Status(val statusCode: Int) {
        PENDING(0),
        APPROVED(1),
        RECEIVED(2),
        FINISHED(3),
    }

    companion object {
        fun getOrderStatus(statusCode: Int): Status {
            Status.values().forEach {
                if (it.statusCode == statusCode) {
                    return it
                }
            }
            return Status.PENDING
        }
    }
}
