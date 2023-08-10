package org.thechance.service_restaurant.data

import com.mongodb.reactivestreams.client.MongoClient
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.thechance.service_restaurant.data.collection.CategoryCollection
import org.thechance.service_restaurant.data.collection.CuisineCollection
import org.thechance.service_restaurant.data.collection.MealCollection
import org.thechance.service_restaurant.data.collection.RestaurantCollection

@Single
class DataBaseContainer(client: MongoClient) {

    private val database = client.coroutine.getDatabase(DATABASE_NAME)

    val categoryCollection: CoroutineCollection<CategoryCollection> = database.getCollection(CATEGORY_COLLECTION)

    val restaurantCollection: CoroutineCollection<RestaurantCollection> = database.getCollection(RESTAURANT_COLLECTION)

    val cuisineCollection: CoroutineCollection<CuisineCollection> = database.getCollection(CUISINE_COLLECTION)

    val mealCollection: CoroutineCollection<MealCollection> = database.getCollection(MEAL_COLLECTION)

    companion object {
        const val DATABASE_NAME = "TheChanceBeepBeep"
        const val CATEGORY_COLLECTION = "category"
        const val RESTAURANT_COLLECTION = "restaurant"
        const val MEAL_COLLECTION = "meal"
        const val CUISINE_COLLECTION = "cuisine"
    }
}