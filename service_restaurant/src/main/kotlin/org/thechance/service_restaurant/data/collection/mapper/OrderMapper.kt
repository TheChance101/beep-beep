package org.thechance.service_restaurant.data.collection.mapper

import org.bson.types.ObjectId
import org.thechance.service_restaurant.data.collection.CartCollection
import org.thechance.service_restaurant.data.collection.OrderCollection
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.entity.OrderedMeal

fun Order.toCollection() = OrderCollection(
    id = ObjectId(),
    userId = ObjectId(userId),
    restaurantId = ObjectId(restaurantId),
    meals = meals.map { it.toCollection() },
    totalPrice = totalPrice,
    createdAt = createdAt,
    orderStatus = status.statusCode
)

fun OrderCollection.toEntity() = Order(
    id = id.toString(),
    userId = userId.toString(),
    restaurantId = restaurantId.toString(),
    restaurantName = "",
    restaurantImage = "",
    meals = meals.toMealEntity(),
    totalPrice = totalPrice,
    createdAt = createdAt,
    currency = "",
    status = Order.Status.getOrderStatus(orderStatus),
)

fun CartCollection.MealCollection.toMealEntity() = OrderedMeal(
    meadId = mealId.toString(),
    quantity = quantity,
    name = name,
    image = image,
    price = price
)

fun List<CartCollection.MealCollection>.toMealEntity() = map { it.toMealEntity() }

fun OrderedMeal.toCollection() = CartCollection.MealCollection(
    mealId = ObjectId(meadId),
    name = name,
    image = image,
    quantity = quantity,
    price = price
)


fun List<OrderCollection>.toEntity() = map { it.toEntity() }



