package org.thechance.service_restaurant.data.collection.mapper

import kotlinx.datetime.LocalDateTime
import org.bson.types.ObjectId
import org.thechance.service_restaurant.data.collection.OrderCollection
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.utils.fromEpochMilliseconds
import org.thechance.service_restaurant.domain.utils.toMillis


fun Order.toCollection(): OrderCollection {
    return OrderCollection(
        id = ObjectId(id),
        userId = ObjectId(userId),
        restaurantId = ObjectId(restaurantId),
        meals = meals.map { it.toCollection() },
        totalPrice = totalPrice,
        createdAt = createdAt.toMillis(),
        orderStatus = status.statusCode
    )
}

fun OrderCollection.toEntity(): Order {
    return Order(
        id = id.toString(),
        userId = userId.toString(),
        restaurantId = restaurantId.toString(),
        meals = meals.map { it.toEntity() },
        totalPrice = totalPrice,
        createdAt = LocalDateTime.fromEpochMilliseconds(createdAt),
        status = Order.Status.getOrderStatus(orderStatus)
    )
}

fun OrderCollection.MealCollection.toEntity(): Order.Meal {
    return Order.Meal(
        meadId = mealId.toString(),
        quantity = quantity
    )
}

fun Order.Meal.toCollection(): OrderCollection.MealCollection {
    return OrderCollection.MealCollection(
        mealId = ObjectId(meadId),
        quantity = quantity
    )
}

fun List<OrderCollection>.toEntity(): List<Order> = map { it.toEntity() }