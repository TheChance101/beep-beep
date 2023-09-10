package domain.entity

data class Taxi(
    val id: String,
    val color: String,
    val plate: String,
    val timeToArriveInMints: Int,
)