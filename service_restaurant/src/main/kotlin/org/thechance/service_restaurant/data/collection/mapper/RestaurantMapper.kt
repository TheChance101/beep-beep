package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.entity.Restaurant


fun RestaurantCollection.toEntity() = Restaurant(
    id = id.toString(),
    name = name,
    description = description,
    priceLevel = priceLevel,
    rate = rate,
    phone = phone,
    openingTime = openingTime,
    closingTime = closingTime,
    isDeleted = isDeleted
)


fun List<RestaurantCollection>.toEntity(): List<Restaurant> = map { it.toEntity() }


fun Restaurant.toCollection() = RestaurantCollection(
    name = name,
    description = description,
    priceLevel = priceLevel,
    rate = rate,
    phone = phone,
    openingTime = openingTime,
    closingTime = closingTime,
)
