package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.CartCollection
import org.thechance.service_restaurant.data.collection.relationModels.OrderWithRestaurant
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.entity.OrderedMeal

fun OrderWithRestaurant.toHistoryEntity() = Order(
    id = id.toString(),
    userId = userId.toString(),
    restaurantId = restaurant.id.toString(),
    restaurantName = restaurant.name,
    restaurantImage = restaurant.restaurantImage,
    totalPrice = totalPrice,
    status = Order.Status.getOrderStatus(orderStatus),
    createdAt = createdAt,
    currency = restaurant.currency,
    meals = meals.map { it.toMealHistoryEntity() },
)

fun List<OrderWithRestaurant>.toHistoryEntity() = map { it.toHistoryEntity() }


fun CartCollection.MealCollection.toMealHistoryEntity() = OrderedMeal(
    meadId = mealId.toString(),
    quantity = quantity,
    image = image,
    name = name,
    price = price
)

