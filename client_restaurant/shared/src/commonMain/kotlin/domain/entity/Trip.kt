package domain.entity


data class Trip(
    val clientId: String,
    val orderId: String,
    val restaurantId: String,
    val startPoint: Location,
    val destination: Location,
    val startPointAddress: String,
    val destinationAddress: String,
    val price: Double,
    val isATaxiTrip: Boolean,
)