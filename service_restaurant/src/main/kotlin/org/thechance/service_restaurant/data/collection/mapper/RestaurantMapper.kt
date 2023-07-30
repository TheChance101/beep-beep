package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.entity.Restaurant

fun RestaurantCollection.toEntity(): Restaurant {
    return Restaurant(
        id = id.toString(),
        name = name
    )
}


fun List<RestaurantCollection>.toEntity(): List<Restaurant> = map { it.toEntity() }
