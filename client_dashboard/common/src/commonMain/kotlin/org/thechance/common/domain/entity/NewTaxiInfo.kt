package org.thechance.common.domain.entity

data class NewTaxiInfo(
    val id: String? = null,
    val plateNumber: String,
    val driverUserName: String,
    val driverId: String? = null,
    val carModel: String,
    val selectedCarColor: CarColor,
    val seats: Int
)


