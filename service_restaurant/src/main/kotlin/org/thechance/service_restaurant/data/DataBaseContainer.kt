package org.thechance.service_restaurant.data

import com.mongodb.reactivestreams.client.MongoClient
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.thechance.service_restaurant.data.collection.*

class DataBaseContainer(client: MongoClient) {

    private val database = client.coroutine.getDatabase(DATA_BASE_NAME)

    val categoryCollection: CoroutineCollection<CategoryCollection> = database.getCollection(CATEGORY_COLLECTION)

    val restaurantCollection: CoroutineCollection<RestaurantCollection> = database.getCollection(RESTAURANT_COLLECTION)

    val cuisineCollection: CoroutineCollection<CuisineCollection> = database.getCollection(CUISINE_COLLECTION)

    val mealCollection: CoroutineCollection<MealCollection> = database.getCollection(MEAL_COLLECTION)

    val orderCollection: CoroutineCollection<OrderCollection> = database.getCollection(ORDER_COLLECTION)

    val cartCollection: CoroutineCollection<CartCollection> = database.getCollection(CART_COLLECTION)

    val restaurantPermissionRequestCollection: CoroutineCollection<RestaurantPermissionRequestCollection> =
        database.getCollection(RESTAURANT_PERMISSION_REQUEST_COLLECTION)

    companion object {
        private val DATA_BASE_NAME = System.getenv("DB_NAME").toString()
        const val CATEGORY_COLLECTION = "category"
        const val RESTAURANT_COLLECTION = "restaurant"
        const val MEAL_COLLECTION = "meal"
        const val CUISINE_COLLECTION = "cuisine"
        const val ORDER_COLLECTION = "order"
        const val CART_COLLECTION = "cart"
        const val RESTAURANT_PERMISSION_REQUEST_COLLECTION = "restaurantPermissionRequest"
        const val CART_COLLECTION = "cart"
    }
}