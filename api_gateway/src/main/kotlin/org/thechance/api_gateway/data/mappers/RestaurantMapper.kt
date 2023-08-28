package org.thechance.api_gateway.data.mappers

import org.thechance.api_gateway.data.model.restaurant.RestaurantResource
import org.thechance.api_gateway.endpoints.model.Restaurant

fun RestaurantResource.toRestaurant(): Restaurant {
    return Restaurant(
        id = this.id,
        ownerId = this.ownerId,
        name = this.name,
        description = this.description ?: "",
        priceLevel = this.priceLevel ?: "",
        rate = this.rate ?: 0.0,
        phone = this.phone,
        openingTime = this.openingTime,
        closingTime = this.closingTime,
        address = this.address,
        location = this.location.toLocation(),
        cuisines = cuisines.map { it.toCuisine()}
    )
}