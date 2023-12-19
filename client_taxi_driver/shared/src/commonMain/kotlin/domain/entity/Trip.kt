package domain.entity

data class Trip(
    val id: String,
    val passengerName: String,
    val pickUpLocation: Location,
    val pickUpAddress: String,
    val dropOffLocation: Location,
    val dropOffAddress: String,
){
    enum class Status(val statusCode: Int){
        APPROVED(1),
        RECEIVED(2),
        FINISHED(3);
    }
}