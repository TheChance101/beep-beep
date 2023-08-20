package org.thechance.common.domain.entity

import org.thechance.common.presentation.taxi.TaxiStatus


data class Taxi(
    val id: String,
    val plateNumber: String,
    val color: Int,
    val type: String,
    val seats: Int = 4,
    val username: String = "",
    val status: TaxiStatus = TaxiStatus.ONLINE,
    val trips: String = "1",
)