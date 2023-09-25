package org.thechance.service_restaurant.data.collection.mapper

import org.bson.types.ObjectId
import org.thechance.service_restaurant.data.collection.CartCollection
import org.thechance.service_restaurant.domain.entity.Cart

fun Cart.toCollection(): CartCollection {
    return CartCollection(
        id = ObjectId(id),
        restaurantId = ObjectId(restaurantId),
        meals = meals.map{ it.toCollection() },
        totalPrice = totalPrice,
        currency = currency,
        userId = ObjectId(userId)
    )
}

fun CartCollection.toEntity(): Cart {
    return Cart(
        id = id.toString(),
        restaurantId = restaurantId.toString(),
        userId = userId.toString(),
        meals = meals.map { it.toEntity() },
        totalPrice = totalPrice,
        currency = currency ?: ""
    )
}

fun Cart.Meal.toCollection(): CartCollection.MealCollection {
    return CartCollection.MealCollection(
        mealId = ObjectId(mealId),
        quantity = quantity,
        price = price
    )
}

fun CartCollection.MealCollection.toEntity(): Cart.Meal {
    return Cart.Meal(
        mealId = mealId.toString(),
        quantity = quantity,
        price = price
    )
}