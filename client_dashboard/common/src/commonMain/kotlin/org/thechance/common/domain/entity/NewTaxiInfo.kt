package org.thechance.common.domain.entity

data class NewTaxiInfo(
    val plateNumber: String,
    val driverUserName: String,
    val carModel: String,
    val selectedCarColor: CarColor,
    val seats: Int
)


