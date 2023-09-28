package org.thechance.common.domain.entity

import org.thechance.common.domain.util.TaxiStatus

data class Taxi(
    val id: String,
    val plateNumber: String,
    val color: CarColor,
    val type: String,
    val seats: Int,
    val username: String,
    val driverId:String? = null,
    val status: TaxiStatus,
    val trips: String,
)