package org.thechance.service_restaurant.data.gateway

import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Updates
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.aggregate
import org.litote.kmongo.eq
import org.litote.kmongo.id.WrappedObjectId
import org.litote.kmongo.`in`
import org.litote.kmongo.lookup
import org.litote.kmongo.match
import org.litote.kmongo.ne
import org.litote.kmongo.project
import org.litote.kmongo.pullAll
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.AddressCollection
import org.thechance.service_restaurant.data.collection.CategoryCollection
import org.thechance.service_restaurant.data.collection.RestaurantAddressesCollection
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.data.collection.toEntity
import org.thechance.service_restaurant.entity.Address
import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.entity.Restaurant
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway
import org.thechance.service_restaurant.utils.isSuccessfullyUpdated


@Single
class RestaurantGatewayImp(private val container: DataBaseContainer) : RestaurantGateway {

    private val restaurantCollection by lazy { container.database.getCollection<RestaurantCollection>() }
    private val categoryCollection by lazy { container.database.getCollection<CategoryCollection>() }
    private val addressCollection by lazy { container.database.getCollection<AddressCollection>() }

    //region Address
    override suspend fun getAddresses(): List<Address> {
        return addressCollection.find(AddressCollection::isDeleted eq false).toList().toEntity()
    }

    override suspend fun getAddress(id: String): Address? {
        return addressCollection.findOneById(ObjectId(id))?.takeIf { !it.isDeleted }?.toEntity()
    }

    override suspend fun addAddress(address: Address): Boolean {
        return addressCollection.insertOne(address.toCollection()).wasAcknowledged()
    }

    override suspend fun updateAddress(address: Address): Boolean {
        return addressCollection.updateOneById(
            id = ObjectId(address.id),
            update = address.toCollection(),
            updateOnlyNotNullProperties = true
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteAddress(id: String): Boolean {
        return addressCollection.updateOneById(
            id = ObjectId(id),
            update = Updates.set(AddressCollection::isDeleted.name, true),
        ).isSuccessfullyUpdated()
    }

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

    //region Category
    override suspend fun getCategories(): List<Category> {
        return categoryCollection.aggregate<CategoryCollection>(
            match(CategoryCollection::isDeleted eq false),
            project(
                CategoryCollection::name,
                CategoryCollection::id
            )
        ).toList().toEntity()
    }

    override suspend fun getCategory(categoryId: String): Category? {
        return categoryCollection.aggregate<CategoryCollection>(
            match(
                and(
                    CategoryCollection::id eq ObjectId(categoryId),
                    CategoryCollection::isDeleted eq false
                )
            ),
            project(
                CategoryCollection::name,
                CategoryCollection::id
            )
        ).toList().firstOrNull()?.toEntity()
    }

    override suspend fun addCategory(category: Category): Boolean {
        return categoryCollection.insertOne(category.toCollection()).wasAcknowledged()
    }

    override suspend fun updateCategory(category: Category): Boolean {
        return categoryCollection.updateOneById(
            id = ObjectId(category.id),
            update = category.toCollection(),
            updateOnlyNotNullProperties = true
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteCategory(categoryId: String): Boolean {
        return categoryCollection.updateOneById(
            id = ObjectId(categoryId),
            update = Updates.set(CategoryCollection::isDeleted.name, true),
        ).isSuccessfullyUpdated()
    }

    override suspend fun addRestaurantsToCategory(
        categoryId: String,
        restaurantIds: List<String>
    ): Boolean {
        return categoryCollection.updateOneById(
            ObjectId(categoryId),
            update = Updates.addEachToSet(
                CategoryCollection::restaurantIds.name,
                restaurantIds.map { ObjectId(it) })
        ).isSuccessfullyUpdated()
    }

    override suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant> {
        return categoryCollection.aggregate<CategoryCollection>(
            match(CategoryCollection::id eq ObjectId(categoryId)),
            lookup(
                from = "restaurantCollection",
                localField = CategoryCollection::restaurantIds.name,
                foreignField = "_id",
                newAs = CategoryCollection::restaurants.name
            ),
            project(
                CategoryCollection::restaurants
            )
        ).toList().firstOrNull()?.restaurants?.map {
            it.toEntity(getAddressesInRestaurant(it.id.toHexString()))
        } ?: emptyList()
    }
    //endregion

    //region Restaurant
    override suspend fun getRestaurants(): List<Restaurant> {
        return restaurantCollection.find(RestaurantCollection::isDeleted eq false).toFlow().map {
            it.toEntity(getAddressesInRestaurant(it.id.toHexString()))
        }.toList()
    }

    override suspend fun getRestaurant(id: String): Restaurant? {
        val addresses = getAddressesInRestaurant(id)
        return restaurantCollection.findOneById(ObjectId(id))?.takeIf { !it.isDeleted }
            ?.toEntity(addresses)
    }

    override suspend fun addRestaurant(restaurant: Restaurant): Boolean {
        return restaurantCollection.insertOne(restaurant.toCollection()).wasAcknowledged()
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
    //endregion
}