package domain.entity

data class DeliveryRide(
    val id: String,
    val startPoint: Location,
    val destination: Location,
    val status: Int,
)