package data.remote.mapper

import data.remote.model.AddressInfoDto
import data.remote.model.LocationDto
import domain.entity.AddressInfo
import domain.entity.Location


fun LocationDto.toEntity(): Location {
    return Location(
        latitude = latitude ?: 0.0,
        longitude = longitude ?: 0.0
    )
}

fun Location.toDto(): LocationDto {
    return LocationDto(
        latitude = latitude,
        longitude = longitude
    )
}
fun AddressInfoDto.toEntity(): AddressInfo {
    return AddressInfo(
        location = location?.toEntity()?: Location(0.0,0.0),
        address = address?:""
    )
}
