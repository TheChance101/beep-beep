package data.remote.model

data class OrderDto(
    val id: String?=null,
    val passengerName: String?=null,
    val passengerId: String?=null,
    val pickUpAddress: String?=null,
    val dropOffAddress: String?=null,
)
