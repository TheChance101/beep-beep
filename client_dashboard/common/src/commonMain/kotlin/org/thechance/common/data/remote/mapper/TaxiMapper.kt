package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.TaxiDto
import org.thechance.common.data.remote.model.TaxiFiltrationDto
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.TaxiFiltration
import org.thechance.common.domain.util.TaxiStatus

fun TaxiDto.toEntity() = Taxi(
    id = id ?: "",
    plateNumber = plateNumber ?: "",
    color = getCarColor(color ?: 4),
    type = type ?: "",
    seats = seats ?: 0,
    status = getTaxiStatus(status ?: 0),
    username = username ?: "",
    trips = trips ?: "0",
)

fun NewTaxiInfo.toDto(): TaxiDto {
    return TaxiDto(
        id = null,
        plateNumber = plateNumber,
        color = setCarColor(selectedCarColor),
        type = carModel,
        seats = seats,
        username = driverUserName,
        status = null,
        trips = null
    )
}

fun TaxiFiltration.toDto(): TaxiFiltrationDto {
    return TaxiFiltrationDto(
        color = setCarColor(carColor),
        seats = seats,
        status = setTaxiStatus(status),
    )
}


fun List<TaxiDto>.toEntity() = map(TaxiDto::toEntity)

fun setCarColor(color: CarColor) =
    when (color) {
        CarColor.RED -> 0
        CarColor.YELLOW -> 1
        CarColor.GREEN -> 2
        CarColor.BLUE -> 3
        CarColor.GREY -> 5
        else -> 4
    }

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

fun setTaxiStatus(status: TaxiStatus) =
    when (status) {
        TaxiStatus.OFFLINE -> 0
        TaxiStatus.ONLINE -> 1
        else -> 2
    }