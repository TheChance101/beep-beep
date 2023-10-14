package org.thechance.service_restaurant.data.collection.mapper

import org.bson.types.ObjectId
import org.thechance.service_restaurant.data.collection.CartCollection
import org.thechance.service_restaurant.data.collection.MealCollection
import org.thechance.service_restaurant.data.collection.relationModels.MealWithCuisines
import org.thechance.service_restaurant.data.collection.relationModels.MealWithRestaurant
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.MealDetails
import java.util.*

fun MealDetails.toCollection(): MealCollection =
    MealCollection(
        name = name,
        restaurantId = ObjectId(restaurantId),
        description = description,
        price = price,
        currency = currency,
        cuisines = cuisines.map { cuisine ->
            ObjectId(cuisine.id)
        },
        image = image
    )

fun MealCollection.toMealEntity() = Meal(
    id = id.toString(),
    restaurantId = restaurantId.toString(),
    restaurantName = "",
    name = name,
    description = description,
    price = price,
    currency = currency,
    image = image
)

fun MealWithCuisines.toEntity() = MealDetails(
    id = id.toString(),
    restaurantId = restaurantId.toString(),
    name = name,
    description = description,
    price = price,
    currency = currency,
    cuisines = cuisines.toEntity(),
    image = image
)

fun List<MealCollection>.toMealEntity(): List<Meal> = this.map { it.toMealEntity() }

fun MealCollection.toMealInCart(quantity: Int) = CartCollection.MealCollection(
    mealId = id,
    name = name,
    image = image,
    quantity = quantity,
    price = price
)

fun MealWithRestaurant.toEntity() = Meal(
    id = id.toString(),
    restaurantId = restaurant.id.toString(),
    restaurantName = restaurant.name,
    name = name,
    description = description,
    price = price,
    currency = currency,
    image = image
)


fun List<MealWithRestaurant>.toEntity() = map { it.toEntity() }