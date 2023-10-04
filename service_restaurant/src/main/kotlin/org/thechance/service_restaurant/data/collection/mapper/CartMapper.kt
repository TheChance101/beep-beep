package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.CartCollection
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.data.collection.relationModels.CartDetails
import org.thechance.service_restaurant.domain.entity.Cart


fun CartDetails.toEntity() = Cart(
    id = id.toString(),
    userId = userId.toString(),
    restaurantId = if (restaurant?.id == null) {
        null
    } else {
        restaurant.id.toString()
    },
    restaurantImage = restaurant?.restaurantImage,
    restaurantName = restaurant?.name,
    currency = restaurant?.currency,
    meals = meals.toMealEntity(),
    totalPrice = meals.sumOf { it.price * it.quantity }
)

fun CartCollection.toCartDetails(restaurant: RestaurantCollection? = null) = CartDetails(
    id = id,
    userId = userId,
    restaurant = restaurant,
    meals = meals,
)