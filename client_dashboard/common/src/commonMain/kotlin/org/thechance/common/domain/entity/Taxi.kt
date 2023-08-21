package org.thechance.common.domain.entity

import androidx.compose.ui.graphics.Color
import org.thechance.common.domain.util.TaxiColor
import org.thechance.common.domain.util.TaxiStatus


data class Taxi(
    val id: String,
    val plateNumber: String,
    val color: Color,
    val type: String,
    val seats: Int,
    val username: String,
    val status: TaxiStatus,
    val trips: String,
)