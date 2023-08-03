package org.thechance.service_restaurant.data.gateway

import com.mongodb.client.model.Updates
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.litote.kmongo.id.WrappedObjectId
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.AddressCollection
import org.thechance.service_restaurant.data.collection.CategoryCollection
import org.thechance.service_restaurant.data.collection.CategoryRestaurant
import org.thechance.service_restaurant.data.collection.MealCollection
import org.thechance.service_restaurant.data.collection.RestaurantAddressesCollection
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.data.collection.RestaurantMeal
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.utils.isSuccessfullyUpdated
import org.thechance.service_restaurant.data.utils.paginate
import org.thechance.service_restaurant.data.utils.toObjectIds
import org.thechance.service_restaurant.entity.Address
import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.entity.Meal
import org.thechance.service_restaurant.entity.Restaurant

@Single
class RestaurantGatewayImp(private val container: DataBaseContainer) : RestaurantGateway {
    private val restaurantCollection by lazy { container.database.getCollection<RestaurantCollection>() }
    private val categoryCollection by lazy { container.database.getCollection<CategoryCollection>() }
    private val addressCollection by lazy { container.database.getCollection<AddressCollection>() }

    //region Restaurant
    override suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant> {
        return restaurantCollection.find(RestaurantCollection::isDeleted eq false)
            .paginate(page, limit).toList().toEntity()
    }

    //need to change
    override suspend fun getRestaurant(id: String): Restaurant? {
        return restaurantCollection.aggregate<RestaurantCollection>(
            match(and(RestaurantCollection::id eq ObjectId(id), RestaurantCollection::isDeleted eq false)),
        ).toList().firstOrNull()?.toEntity(getAddressesInRestaurant(id))
    }

    override suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category> {
        return restaurantCollection.aggregate<CategoryRestaurant>(
            match(RestaurantCollection::id eq ObjectId(restaurantId)),
            lookup(
                from = "categoryCollection",
                resultProperty = CategoryRestaurant::categories,
                pipeline = listOf(match(CategoryCollection::isDeleted eq false)).toTypedArray()
            ),
        ).toList().first().categories.toEntity()
    }

    override suspend fun getMealsInRestaurant(restaurantId: String): List<Meal> {
       return restaurantCollection.aggregate<RestaurantMeal>(
           match(RestaurantCollection::id eq ObjectId(restaurantId)),
           lookup(
               from = "meal",
               resultProperty = RestaurantMeal::meals,
               pipeline = listOf(match(MealCollection::isDeleted eq false)).toTypedArray()
           ),
       ).toList().first().meals.toEntity()
    }

    override suspend fun addRestaurant(restaurant: Restaurant): Boolean {
        return restaurantCollection.insertOne(restaurant.toCollection()).wasAcknowledged()
    }

    override suspend fun addCategoriesToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        val resultAddToCategory = categoryCollection.updateMany(
            CategoryCollection::id `in` categoryIds.toObjectIds(),
            addToSet(CategoryCollection::restaurantIds, ObjectId(restaurantId))
        ).isSuccessfullyUpdated()

        val resultAddToRestaurant = restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            update = Updates.addEachToSet(RestaurantCollection::categoryIds.name, categoryIds.toObjectIds())
        ).isSuccessfullyUpdated()

        return resultAddToCategory and resultAddToRestaurant
    }

    override suspend fun addMealsToRestaurant(restaurantId: String, mealIds: List<String>): Boolean {
        return restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            update = Updates.addEachToSet(RestaurantCollection::mealIds.name, mealIds.toObjectIds())
        ).isSuccessfullyUpdated()
    }

    override suspend fun updateRestaurant(restaurant: Restaurant): Boolean {
        return restaurantCollection.updateOneById(
            id = ObjectId(restaurant.id),
            update = restaurant.toCollection(),
            updateOnlyNotNullProperties = true
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteRestaurant(restaurantId: String): Boolean {
        return restaurantCollection.updateOneById(
            id = ObjectId(restaurantId),
            update = Updates.set(RestaurantCollection::isDeleted.name, true),
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        val resultDeleteFromCategory = categoryCollection.updateMany(
            CategoryCollection::id `in` categoryIds.toObjectIds(),
            pull(CategoryCollection::restaurantIds, ObjectId(restaurantId))
        ).isSuccessfullyUpdated()

        val resultDeleteFromRestaurant = restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            pullAll(RestaurantCollection::categoryIds, categoryIds.toObjectIds())
        ).isSuccessfullyUpdated()
        return resultDeleteFromRestaurant and resultDeleteFromCategory
    }
    //endregion

    //region addresses
    override suspend fun addAddressesToRestaurant(
        restaurantId: String,
        addressesIds: List<String>
    ): Boolean {
        val addresses = addressCollection.find(
            and(
                AddressCollection::id `in` addressesIds.map { ObjectId(it) },
                AddressCollection::isDeleted ne true
            )
        ).toList()

        return restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            update = Updates.addEachToSet(
                RestaurantCollection::addressIds.name,
                addresses.map { it.id }
            )
        ).isSuccessfullyUpdated()
    }

    override suspend fun getAddressesInRestaurant(restaurantId: String): List<Address> {
        return restaurantCollection.aggregate<RestaurantAddressesCollection>(
            match(
                and(
                    RestaurantCollection::id eq ObjectId(restaurantId),
                    RestaurantCollection::isDeleted ne true
                )
            ),
            lookup(
                from = "addressCollection",
                resultProperty = RestaurantAddressesCollection::addresses,
                pipeline = arrayOf(match(AddressCollection::isDeleted ne true))
            )
        ).toList().firstOrNull()?.addresses?.toEntity() ?: emptyList()
    }

    override suspend fun deleteAddressesInRestaurant(
        restaurantId: String,
        addressesIds: List<String>
    ): Boolean {
        return restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            pullAll(RestaurantCollection::addressIds, addressesIds.map { WrappedObjectId(it) })
        ).isSuccessfullyUpdated()
    }
    //endregion
}