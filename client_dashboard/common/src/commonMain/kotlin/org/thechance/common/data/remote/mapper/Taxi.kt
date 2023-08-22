package org.thechance.common.data.remote.mapper

import androidx.compose.ui.graphics.Color
import org.thechance.common.data.remote.model.TaxiDto
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.util.TaxiColor
import org.thechance.common.domain.util.TaxiStatus

fun TaxiDto.toEntity() = Taxi(
    id = id,
    plateNumber = plateNumber,
    color = colorMap[color] ?: Color.White,
    type = type,
    seats = seats,
    status = statusMap[status] ?: TaxiStatus.ONLINE,
    username = username,
    trips = trips,
)

private val colorMap: Map<Int, Color>
    get() = mapOf(
        RED to TaxiColor.RED.color,
        YELLOW to TaxiColor.YELLOW.color,
        GREEN to TaxiColor.GREEN.color,
        BLUE to TaxiColor.BLUE.color,
        WHITE to TaxiColor.WHITE.color,
        GRAY to TaxiColor.GRAY.color,
        BLACK to TaxiColor.BLACK.color,
    )
private val statusMap: Map<Int, TaxiStatus>
    get() = mapOf(
        OFFLINE to TaxiStatus.OFFLINE,
        ONLINE to TaxiStatus.ONLINE,
        ON_RIDE to TaxiStatus.ON_RIDE,
    )

fun List<TaxiDto>.toEntity() = map { it.toEntity() }

private const val OFFLINE = 0
private const val ONLINE = 1
private const val ON_RIDE = 2

private const val RED = 0
private const val YELLOW = 1
private const val GREEN = 2
private const val BLUE = 3
private const val WHITE = 4
private const val GRAY = 5
private const val BLACK = 6
