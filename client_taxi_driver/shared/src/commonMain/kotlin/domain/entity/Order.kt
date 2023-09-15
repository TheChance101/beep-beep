package domain.entity

data class Order(
    val id: String,
    val passengerName: String,
    val address: String,
    val lat: String,
    val lng: String,
)
