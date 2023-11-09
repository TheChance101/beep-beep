package domain.entity


data class Order(
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
enum class TripStatus(val statusCode: Int) {
    PENDING(0),
    APPROVED(1),
    RECEIVED(2),
    FINISHED(3);

    companion object {
        fun getOrderStatus(statusCode: Int): TripStatus {
            TripStatus.values().forEach {
                if (it.statusCode == statusCode) {
                    return it
                }
            }
            return TripStatus.PENDING
        }
    }
}