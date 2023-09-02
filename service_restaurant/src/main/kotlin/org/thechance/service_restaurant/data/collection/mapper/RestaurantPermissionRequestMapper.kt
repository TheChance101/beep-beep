package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.RestaurantPermissionRequestCollection
import org.thechance.service_restaurant.domain.entity.RestaurantPermissionRequest

fun RestaurantPermissionRequestCollection.toEntity() = RestaurantPermissionRequest(
    id = id.toString(),
    restaurantName = restaurantName,
    ownerEmail = ownerEmail,
    cause = cause,
)

fun List<RestaurantPermissionRequestCollection>.toEntity(): List<RestaurantPermissionRequest> = map { it.toEntity() }
