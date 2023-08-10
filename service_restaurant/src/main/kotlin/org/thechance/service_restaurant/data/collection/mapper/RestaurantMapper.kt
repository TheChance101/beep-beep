package org.thechance.service_restaurant.data.collection.mapper

import org.bson.types.ObjectId
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.domain.entity.Restaurant


fun RestaurantCollection.toEntity() = Restaurant(
    id = id.toString(),
    ownerId = ownerId.toString(),
    name = name,
    description = description,
    priceLevel = priceLevel,
    rate = rate,
    phone = phone,
    openingTime = openingTime,
    closingTime = closingTime,
    address = address.toEntity()
)

fun List<RestaurantCollection>.toEntity(): List<Restaurant> = map { it.toEntity() }


fun Restaurant.toCollection() = RestaurantCollection(
    ownerId = ObjectId(ownerId),
    name = name,
    description = description?:"",
    priceLevel = priceLevel?: "$",
    rate = rate?: 0.0,
    phone = phone,
    openingTime = openingTime,
    closingTime = closingTime,
    address = address.toCollection()
)

