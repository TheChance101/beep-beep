package domain.entity

data class Order(
    val id: String,
    val passengerName: String,
    val pickUpAddress: Location,
    val dropOffAddress: Location,
)
