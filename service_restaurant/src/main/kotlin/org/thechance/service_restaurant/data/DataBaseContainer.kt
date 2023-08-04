package org.thechance.service_restaurant.data

import com.mongodb.reactivestreams.client.MongoClient
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.thechance.service_restaurant.data.collection.*
import org.thechance.service_restaurant.utils.Constants.ADDRESS_COLLECTION
import org.thechance.service_restaurant.utils.Constants.CATEGORY_COLLECTION
import org.thechance.service_restaurant.utils.Constants.CUISINE_COLLECTION
import org.thechance.service_restaurant.utils.Constants.DATABASE_NAME
import org.thechance.service_restaurant.utils.Constants.MEAL_COLLECTION
import org.thechance.service_restaurant.utils.Constants.RESTAURANT_COLLECTION

@Single
class DataBaseContainer(client: MongoClient) {

    private val database = client.coroutine.getDatabase(DATABASE_NAME)

    val categoryCollection: CoroutineCollection<CategoryCollection> = database.getCollection(CATEGORY_COLLECTION)

    val restaurantCollection: CoroutineCollection<RestaurantCollection> = database.getCollection(RESTAURANT_COLLECTION)

    val addressCollection: CoroutineCollection<AddressCollection> = database.getCollection(ADDRESS_COLLECTION)

    val cuisineCollection: CoroutineCollection<CuisineCollection> = database.getCollection(CUISINE_COLLECTION)

    val mealCollection: CoroutineCollection<MealCollection> = database.getCollection(MEAL_COLLECTION)


}