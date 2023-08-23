package org.thechance.common.domain.entity

data class AddTaxi(
    val plateNumber: String,
    val driverUserName: String,
    val carModel: String,
    val selectedCarColor: CarColor,
    val seats: Int
)


