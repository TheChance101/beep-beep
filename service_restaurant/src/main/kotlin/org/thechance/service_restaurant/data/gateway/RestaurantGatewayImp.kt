package org.thechance.service_restaurant.data.gateway

import com.mongodb.client.model.Updates
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.litote.kmongo.id.WrappedObjectId
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.*
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.utils.isSuccessfullyUpdated
import org.thechance.service_restaurant.data.utils.paginate
import org.thechance.service_restaurant.data.utils.toObjectIds
import org.thechance.service_restaurant.domain.entity.Address
import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.RestaurantGateway
import org.thechance.service_restaurant.utils.Constants.ADDRESS_COLLECTION
import org.thechance.service_restaurant.utils.Constants.CATEGORY_COLLECTION
import org.thechance.service_restaurant.utils.Constants.CUISINE_COLLECTION

@Single
class RestaurantGatewayImp(private val container: DataBaseContainer) : RestaurantGateway {

    //region Restaurant
    override suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant> {
        return container.restaurantCollection.find(RestaurantCollection::isDeleted eq false)
            .paginate(page, limit).toList().toEntity()
    }

    override suspend fun getRestaurant(id: String): Restaurant? {
        return container.restaurantCollection.aggregate<RestaurantCollection>(
            match(
                and(
                    RestaurantCollection::id eq ObjectId(id),
                    RestaurantCollection::isDeleted eq false
                )
            ),
        ).toList().firstOrNull()?.toEntity(getAddressesInRestaurant(id))
    }

    override suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category> {
        return container.restaurantCollection.aggregate<CategoryRestaurant>(
            match(RestaurantCollection::id eq ObjectId(restaurantId)),
            lookup(
                from = CATEGORY_COLLECTION,
                localField = RestaurantCollection::categoryIds.name,
                foreignField = "_id",
                newAs = CategoryRestaurant::categories.name
            )
        ).toList().first().categories.filterNot { it.isDeleted }.toEntity()
    }

    override suspend fun getCuisineInRestaurant(restaurantId: String): List<Cuisine> {
        return container.restaurantCollection.aggregate<RestaurantCuisine>(
            match(RestaurantCollection::id eq ObjectId(restaurantId)),
            lookup(
                from = CUISINE_COLLECTION,
                localField = RestaurantCollection::cuisineIds.name,
                foreignField = "_id",
                newAs = RestaurantCuisine::cuisines.name
            )
        ).toList().first().cuisines.filterNot { it.isDeleted }.toEntity()
    }

    override suspend fun addRestaurant(restaurant: Restaurant): Boolean {
        return container.restaurantCollection.insertOne(restaurant.toCollection()).wasAcknowledged()
    }

    override suspend fun addCategoriesToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        val resultAddToCategory = container.categoryCollection.updateMany(
            CategoryCollection::id `in` categoryIds.toObjectIds(),
            addToSet(CategoryCollection::restaurantIds, ObjectId(restaurantId))
        ).isSuccessfullyUpdated()

        val resultAddToRestaurant = container.restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            update = Updates.addEachToSet(RestaurantCollection::categoryIds.name, categoryIds.toObjectIds())
        ).isSuccessfullyUpdated()

        return resultAddToCategory and resultAddToRestaurant
    }

    override suspend fun addCuisineToRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean {
        return container.restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            update = Updates.addEachToSet(RestaurantCollection::cuisineIds.name, cuisineIds.toObjectIds())
        ).isSuccessfullyUpdated()
    }

    override suspend fun updateRestaurant(restaurant: Restaurant): Boolean {
        return container.restaurantCollection.updateOneById(
            id = ObjectId(restaurant.id),
            update = restaurant.toCollection(),
            updateOnlyNotNullProperties = true
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteRestaurant(restaurantId: String): Boolean {
        return container.restaurantCollection.updateOneById(
            id = ObjectId(restaurantId),
            update = Updates.set(RestaurantCollection::isDeleted.name, true),
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        val resultDeleteFromCategory = container.categoryCollection.updateMany(
            CategoryCollection::id `in` categoryIds.toObjectIds(),
            pull(CategoryCollection::restaurantIds, ObjectId(restaurantId))
        ).isSuccessfullyUpdated()

        val resultDeleteFromRestaurant = container.restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            pullAll(RestaurantCollection::categoryIds, categoryIds.toObjectIds())
        ).isSuccessfullyUpdated()
        return resultDeleteFromRestaurant and resultDeleteFromCategory
    }

    //need to delete in both collection
    override suspend fun deleteCuisinesInRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean {
        return container.restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            pullAll(RestaurantCollection::cuisineIds, cuisineIds.toObjectIds())
        ).isSuccessfullyUpdated()
    }
    //endregion

    //region addresses
    override suspend fun addAddressesToRestaurant(
        restaurantId: String,
        addressesIds: List<String>
    ): Boolean {
        val addresses = container.addressCollection.find(
            and(
                AddressCollection::id `in` addressesIds.map { ObjectId(it) },
                AddressCollection::isDeleted ne true
            )
        ).toList()

        return container.restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            update = Updates.addEachToSet(
                RestaurantCollection::addressIds.name,
                addresses.map { it.id }
            )
        ).isSuccessfullyUpdated()
    }

    override suspend fun getAddressesInRestaurant(restaurantId: String): List<Address> {
        return container.restaurantCollection.aggregate<RestaurantAddressesCollection>(
            match(
                and(
                    RestaurantCollection::id eq ObjectId(restaurantId),
                    RestaurantCollection::isDeleted ne true
                )
            ),
            lookup(
                from = ADDRESS_COLLECTION,
                resultProperty = RestaurantAddressesCollection::addresses,
                pipeline = arrayOf(match(AddressCollection::isDeleted ne true))
            )
        ).toList().firstOrNull()?.addresses?.toEntity() ?: emptyList()
    }

    override suspend fun deleteAddressesInRestaurant(
        restaurantId: String,
        addressesIds: List<String>
    ): Boolean {
        return container.restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            pullAll(RestaurantCollection::addressIds, addressesIds.map { WrappedObjectId(it) })
        ).isSuccessfullyUpdated()
    }
    //endregion
}