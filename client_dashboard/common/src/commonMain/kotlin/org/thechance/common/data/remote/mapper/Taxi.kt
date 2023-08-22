package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.TaxiDto
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.util.TaxiStatus

fun TaxiDto.toEntity() = Taxi(
    id = id,
    plateNumber = plateNumber,
    color = getCarColor(color),
    type = type,
    seats = seats,
    status = getTaxiStatus(status),
    username = username,
    trips = trips,
)

fun getCarColor(color: Int) =
    when (color) {
        0 -> CarColor.RED
        1 -> CarColor.YELLOW
        2 -> CarColor.GREEN
        3 -> CarColor.BLUE
        5 -> CarColor.GREY
        else -> CarColor.BLACK
    }

fun getTaxiStatus(status: Int) =
    when (status) {
        0 -> TaxiStatus.OFFLINE
        1 -> TaxiStatus.ONLINE
        else -> TaxiStatus.ON_RIDE
    }
fun List<TaxiDto>.toEntity() = map { it.toEntity() }


