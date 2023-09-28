package org.thechance.common.data.gateway.remote.mapper

import org.thechance.common.data.gateway.remote.model.TaxiDto
import org.thechance.common.data.gateway.remote.model.TaxiFiltrationDto
import org.thechance.common.data.gateway.remote.model.TaxiInfoDto
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.TaxiFiltration
import org.thechance.common.domain.util.TaxiStatus

fun TaxiDto.toEntity() = Taxi(
    id = id ?: "",
    plateNumber = plateNumber ?: "",
    color = getCarColor(color!!),
    type = type ?: "",
    seats = seats ?: 0,
    status = getTaxiStatus(isAvailable ?: false),
    username = driverUsername ?: "",
    trips = trips ?: "0",
    driverId = driverId ?: "",
)

fun NewTaxiInfo.toDto(): TaxiInfoDto {
    return TaxiInfoDto(
        plateNumber = plateNumber,
        color = setCarColor(selectedCarColor).toString(),
        type = carModel,
        seats = seats,
        driverUsername = driverUserName,
        driverId = driverId,
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

fun setCarColor(color: CarColor?) =
    when (color) {
        CarColor.BLACK -> 4278190080L
        CarColor.WHITE -> 4294967295L
        CarColor.RED -> 4294901760L
        CarColor.BLUE -> 4278190335L
        CarColor.GREEN -> 4278255360L
        CarColor.YELLOW -> 4294639360L
        CarColor.ORANGE -> 4294944768L
        CarColor.PURPLE -> 4288545023L
        CarColor.PINK -> 4293591295L
        CarColor.BROWN -> 4283578920L
        CarColor.GRAY -> 4290626507L
        CarColor.SILVER -> 4290822336L
        CarColor.GOLD -> 4294956800L
        CarColor.BRONZE -> 4291657522L
        else -> 0L
    }

fun getCarColor(color: Long) =
    when (color) {
        4278190080L -> CarColor.BLACK
        4294967295L -> CarColor.WHITE
        4294901760L -> CarColor.RED
        4278190335L -> CarColor.BLUE
        4278255360L -> CarColor.GREEN
        4294639360L -> CarColor.YELLOW
        4294944768L -> CarColor.ORANGE
        4288545023L -> CarColor.PURPLE
        4293591295L -> CarColor.PINK
        4283578920L -> CarColor.BROWN
        4290626507L -> CarColor.GRAY
        4290822336L -> CarColor.SILVER
        4294956800L -> CarColor.GOLD
        4291657522L -> CarColor.BRONZE
        else -> CarColor.WHITE
    }

fun getTaxiStatus(isAvailable: Boolean) =
    if (isAvailable) TaxiStatus.ONLINE else TaxiStatus.OFFLINE


fun setTaxiStatus(status: TaxiStatus?) =
    if (status == TaxiStatus.OFFLINE) 0 else 1
