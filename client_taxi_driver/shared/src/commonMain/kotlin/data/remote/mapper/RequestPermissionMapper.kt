package data.remote.mapper

import data.remote.model.TaxiRequestPermissionDto
import domain.entity.TaxiRequestPermission


fun TaxiRequestPermission.toDto() = TaxiRequestPermissionDto(
    driverFullName = driverFullName,
    driverEmail = driverEmail,
    description = description,
)

fun TaxiRequestPermissionDto.toEntity() = TaxiRequestPermission(
    driverFullName = driverFullName,
    driverEmail = driverEmail,
    description = description,
)