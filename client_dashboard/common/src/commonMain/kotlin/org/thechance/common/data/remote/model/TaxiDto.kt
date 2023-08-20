package org.thechance.common.data.remote.model

import org.thechance.common.domain.entity.Taxi
import org.thechance.common.presentation.taxi.TaxiStatus


data class TaxiDto(
    val id: String,
    val plateNumber: String,
    val color: Int,
    val type: String,
    val seats: Int = 4,
    val username: String = "",
    val status: TaxiStatus = TaxiStatus.ONLINE,
    val trips: String = "",
)