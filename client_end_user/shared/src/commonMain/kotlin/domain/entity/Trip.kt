package domain.entity

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
    val price: Double,
    val startDate: String,
    val endDate: String,
    val timeToArriveInMints: Int,
)