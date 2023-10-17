package data.remote.model

data class OrderDto(
    val id: String?,
    val passengerName: String?,
    val passengerId: String?,
    val pickUpAddress: LocationDto,
    val dropOffAddress: LocationDto,
)
