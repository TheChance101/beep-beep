package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.RestaurantPermissionRequestDto
import org.thechance.service_restaurant.domain.entity.RestaurantPermissionRequest

fun RestaurantPermissionRequestDto.toEntity(): RestaurantPermissionRequest {
    return RestaurantPermissionRequest(
        id = this.id ?: "",
        restaurantName = this.restaurantName ?: "",
        ownerEmail = this.ownerEmail ?: "",
        cause = this.cause ?: "",
    )
}

fun RestaurantPermissionRequest.toDto(): RestaurantPermissionRequestDto {
    return RestaurantPermissionRequestDto(
        id = this.id,
        restaurantName = this.restaurantName,
        ownerEmail = this.ownerEmail,
        cause = this.cause,
    )
}

fun List<RestaurantPermissionRequest>.toDto(): List<RestaurantPermissionRequestDto> = map { it.toDto() }
