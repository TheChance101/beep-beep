package domain.entity

data class Order(
    val id: String,
    val passengerName: String,
    val pickUpAddress: String,
    val dropOffAddress:String,
)
