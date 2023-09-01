package org.thechance.common.domain.entity

data class Restaurant(
    val id: String,
    val name: String,
    val ownerUsername: String,
    val phoneNumber: String,
    val rating: Double,
    val priceLevel: Int,
    val workingHours: Pair<CustomTime, CustomTime>, // Pair representing (open time, closing time)
)

data class CustomTime(val hour: Int, val minute: Int) {
    init {
        require(hour in 1..24) { "Hours must be between 1 and 24" }
        require(minute in 1..59) { "Minutes must be between 1 and 59" }
    }

    override fun toString(): String = String.format("%02d:%02d", hour, minute)

}

fun Pair<CustomTime, CustomTime>.toWorkingHoursString() = "$first - $second"

fun parseToCustomTime(time: String): CustomTime =
    CustomTime(time.split(":")[0].toInt(), time.split(":")[1].toInt())