package data.remote.mapper

import data.remote.model.DeliveryRequestPermissionDto
import domain.entity.DeliveryRequestPermission

fun DeliveryRequestPermission.toDto(): DeliveryRequestPermissionDto {

    return DeliveryRequestPermissionDto(
        restaurantName = restaurantName,
        ownerEmail = email,
        cause = description
    )
}