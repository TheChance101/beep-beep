package domain.entity

data class Taxi(
    val id: String,
    val plateNumber: String,
    val color: Long,
    val type: String,
    val driverId: String,
    val driverUsername: String,
    val driverImage: String,
    val rate: Double,
    val isAvailable: Boolean,
    val seats: Int,
    val tripsCount: Int,
)