package org.thechance.api_gateway.data.mappers

import org.thechance.api_gateway.data.model.restaurant.LocationResource
import org.thechance.api_gateway.endpoints.model.Location

fun LocationResource.toLocation(): Location{
    return Location(
        latitude = this.latitude,
        longitude = this.longitude
    )
}