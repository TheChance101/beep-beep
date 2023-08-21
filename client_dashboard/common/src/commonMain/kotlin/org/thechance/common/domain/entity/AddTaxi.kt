package org.thechance.common.domain.entity


data class AddTaxi(
    val taxiPlateNumber: String,
    val driverUserName: String,
    val carModel: String,
    val selectedCarColor: CarColor,
    val seats: Int
)


