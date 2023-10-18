package domain.entity

import kotlinx.datetime.LocalDate

data class Trip(
    val id: String,
    val taxiId: String,
    val taxiPlateNumber: String,
    val driverId: String,
    val driverName: String,
    val clientId: String,
    val startPoint: Location,
    val destination: Location,
    val rate: Double,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val timeToArriveInMints: Int,
    val price: Price
)
