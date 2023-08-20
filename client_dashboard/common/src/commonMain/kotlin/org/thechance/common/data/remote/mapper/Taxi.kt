package org.thechance.common.data.remote.mapper

import androidx.compose.ui.graphics.Color
import org.thechance.common.data.remote.model.TaxiDto
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.util.TaxiColor
import org.thechance.common.domain.util.TaxiStatus

fun TaxiDto.toEntity() = Taxi(
    id = id,
    plateNumber = plateNumber,
    color = colorMapper[color] ?: Color.White,
    type = type,
    seats = seats,
    status = statusMapper[status]?: TaxiStatus.ONLINE,
    username = username,
    trips = trips,
)

private val colorMapper: Map<Int, Color> = mapOf(
    TaxiColor.RED.colorNumber to Color(0xFFF47373),
    TaxiColor.YELLOW.colorNumber to Color(0xFFF8EC7E),
    TaxiColor.GREEN.colorNumber to Color(0xFF80E5AB),
    TaxiColor.BLUE.colorNumber to Color(0xFF77DEEE),
    TaxiColor.WHITE.colorNumber to Color.White,
    TaxiColor.GRAY.colorNumber to Color(0xFFAFAFAF),
    TaxiColor.BLACK.colorNumber to Color(0xFF3F3F3F),
)
private val statusMapper: Map<Int, TaxiStatus> = mapOf(
    0 to TaxiStatus.OFFLINE,
    1 to TaxiStatus.ONLINE,
    2 to TaxiStatus.ON_RIDE,
)

fun List<TaxiDto>.toEntity() = map { it.toEntity() }