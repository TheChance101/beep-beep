package domain.entity

import kotlinx.datetime.LocalTime

data class Trip(
    val id: String,
    val taxiId: String? = null,
    val driverId: String? = null,
    val clientId: String,
    val orderId: String? = null,
    val restaurantId: String? = null,
    val taxiPlateNumber: String? = null,
    val taxiDriverName: String? = null,
    val taxiColor: TaxiColor,
    val startPoint: Location,
    val destination: Location,
    val startPointAddress: String,
    val destinationAddress: String,
    val rate: Double,
    val price: Double,
    val startDate: String? = null,
    val endDate: String? = null,
    val isATaxiTrip: Boolean,
    val tripStatus: TripStatus,
    val timeToArriveInMints: Int,
) {

    val estimatedTimeToArriveInMints: Int
        get() = when (TripStatus.getTripStatus(tripStatus.statusCode)) {
            TripStatus.PENDING -> LocalTime(0, 40).minute
            TripStatus.APPROVED -> {
                if (timeToArriveInMints == 0)
                    LocalTime(0, 30).minute else timeToArriveInMints
            }

            TripStatus.RECEIVED -> timeToArriveInMints / 2
            TripStatus.FINISHED -> LocalTime(0, 0).minute
        }


}