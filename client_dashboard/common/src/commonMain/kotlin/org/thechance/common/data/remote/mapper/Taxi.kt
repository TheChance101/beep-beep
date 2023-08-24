package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.TaxiDto
import org.thechance.common.domain.entity.AddTaxi
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.util.TaxiStatus

fun TaxiDto.toEntity() = Taxi(
    id = id?: "",
    plateNumber = plateNumber,
    color = getCarColor(color),
    type = type,
    seats = seats,
    status = getTaxiStatus(status?:0),
    username = username,
    trips = trips?: "0",
)

fun AddTaxi.toDto(): TaxiDto {
   return TaxiDto(
        plateNumber = plateNumber,
        color = setCarColo(selectedCarColor),
        type = carModel,
        seats = seats,
        username = driverUserName,
    )
}
fun List<TaxiDto>.toEntity() = map(TaxiDto::toEntity)

fun getCarColor(color: Int) =
    when (color) {
        0 -> CarColor.RED
        1 -> CarColor.YELLOW
        2 -> CarColor.GREEN
        3 -> CarColor.BLUE
        5 -> CarColor.GREY
        else -> CarColor.BLACK
    }
fun setCarColo(color: CarColor) =
    when (color) {
        CarColor.RED -> 0
        CarColor.YELLOW -> 1
        CarColor.GREEN -> 2
        CarColor.BLUE -> 3
        CarColor.GREY -> 5
        else -> 4
    }

fun getTaxiStatus(status: Int) =
    when (status) {
        0 -> TaxiStatus.OFFLINE
        1 -> TaxiStatus.ONLINE
        else -> TaxiStatus.ON_RIDE
    }