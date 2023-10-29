package domain.entity

data class DeliveryRide(
    val id: String,
    val restaurantName: String,
    val restaurantImage: String,
    val startPoint: Location,
    val destination: Location,
    val startPointAddress: String,
    val destinationAddress: String,
    val price: Double,
    val tripStatus: TripStatus,
)