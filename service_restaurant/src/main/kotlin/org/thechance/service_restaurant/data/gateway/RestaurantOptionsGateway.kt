package org.thechance.service_restaurant.data.gateway

import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Updates
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.*
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.collection.relationModels.CategoryRestaurant
import org.thechance.service_restaurant.data.collection.relationModels.MealCuisines
import org.thechance.service_restaurant.data.utils.isSuccessfullyUpdated
import org.thechance.service_restaurant.data.utils.paginate
import org.thechance.service_restaurant.data.utils.toObjectIds
import org.thechance.service_restaurant.domain.entity.*
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND

class RestaurantOptionsGateway(private val container: DataBaseContainer) : IRestaurantOptionsGateway {

    //region Category
    override suspend fun getCategories(page: Int, limit: Int): List<Category> {
        return container.categoryCollection.find(CategoryCollection::isDeleted eq false)
            .paginate(page, limit).toList().toEntity()
    }

    override suspend fun getCategory(categoryId: String): Category? {
        return container.categoryCollection.aggregate<CategoryCollection>(
            match(
                and(
                    CategoryCollection::id eq ObjectId(categoryId),
                    CategoryCollection::isDeleted eq false
                )
            ),
            project(CategoryCollection::name, CategoryCollection::id)
        ).toList().firstOrNull()?.toEntity()
    }

    override suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant> {
        return container.categoryCollection.aggregate<CategoryRestaurant>(
            match(CategoryCollection::id eq ObjectId(categoryId)),
            lookup(
                from = DataBaseContainer.RESTAURANT_COLLECTION,
                localField = CategoryCollection::restaurantIds.name,
                foreignField = "_id",
                newAs = CategoryRestaurant::restaurants.name
            )
        ).toList().first().restaurants.filterNot { it.isDeleted }.toEntity()
    }

    override suspend fun areCategoriesExisting(categoryIds: List<String>): Boolean {
        val categoryObjects =
            container.categoryCollection.find(
                and(
                    CategoryCollection::id `in` categoryIds.toObjectIds(),
                    CategoryCollection::isDeleted eq false
                )
            ).toList()
        return categoryObjects.size == categoryIds.size

    }

    override suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category> {
        return container.restaurantCollection.aggregate<CategoryRestaurant>(
            match(RestaurantCollection::id eq ObjectId(restaurantId)),
            lookup(
                from = DataBaseContainer.CATEGORY_COLLECTION,
                localField = RestaurantCollection::categoryIds.name,
                foreignField = "_id",
                newAs = CategoryRestaurant::categories.name
            )
        ).toList().first().categories.filterNot { it.isDeleted }.toEntity()
    }

    override suspend fun addCategory(category: Category): Category {
        val addedCategory = category.toCollection()
        container.categoryCollection.insertOne(addedCategory)
        return addedCategory.toEntity()
    }

    override suspend fun addCategoriesToRestaurant(
        restaurantId: String,
        categoryIds: List<String>
    ): Boolean {
        val resultAddToCategory = container.categoryCollection.updateMany(
            CategoryCollection::id `in` categoryIds.toObjectIds(),
            addToSet(CategoryCollection::restaurantIds, ObjectId(restaurantId))
        ).isSuccessfullyUpdated()

        val resultAddToRestaurant = container.restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            update = Updates.addEachToSet(
                RestaurantCollection::categoryIds.name,
                categoryIds.toObjectIds()
            )
        ).isSuccessfullyUpdated()

        return resultAddToCategory and resultAddToRestaurant
    }

    override suspend fun updateCategory(category: Category): Category {
        return container.categoryCollection.findOneAndUpdate(
            filter = CategoryCollection::id eq ObjectId(category.id),
            update = category.toCollection(),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )?.toEntity() ?: throw MultiErrorException(listOf(NOT_FOUND))
    }

    override suspend fun deleteCategory(categoryId: String): Boolean {
        return container.categoryCollection.updateOneById(
            id = ObjectId(categoryId),
            update = Updates.set(CategoryCollection::isDeleted.name, true),
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteRestaurantsInCategory(
        categoryId: String,
        restaurantIds: List<String>
    ): Boolean {
        val resultDeleteFromRestaurant = container.restaurantCollection.updateMany(
            RestaurantCollection::id `in` restaurantIds.toObjectIds(),
            pull(RestaurantCollection::categoryIds, ObjectId(categoryId))
        ).isSuccessfullyUpdated()

        val resultDeleteFromCategory = container.categoryCollection.updateOneById(
            ObjectId(categoryId),
            pullAll(CategoryCollection::restaurantIds, restaurantIds.toObjectIds())
        ).isSuccessfullyUpdated()
        return resultDeleteFromRestaurant and resultDeleteFromCategory
    }
    //endregion

    //region Cuisines
    override suspend fun getCuisines(): List<Cuisine> =
        container.cuisineCollection.find(MealCollection::isDeleted eq false).toList().toEntity()

    override suspend fun getCuisineById(id: String): Cuisine? =
        container.cuisineCollection.findOneById(ObjectId(id))?.takeIf { !it.isDeleted }?.toEntity()

    override suspend fun getMealsInCuisine(cuisineId: String): List<Meal> {
        return container.cuisineCollection.aggregate<MealCuisines>(
            match(CuisineCollection::id eq ObjectId(cuisineId)),
            lookup(
                from = DataBaseContainer.MEAL_COLLECTION,
                localField = CuisineCollection::meals.name,
                foreignField = "_id",
                newAs = MealCuisines::meals.name
            )
        ).toList().first().meals.filterNot { it.isDeleted }.toEntity()
    }

    override suspend fun addCuisine(cuisine: Cuisine): Cuisine {
        val addedCuisine = cuisine.toCollection()
        val updatedCuisine = container.cuisineCollection.findOneAndUpdate(
            filter = CuisineCollection::name eq cuisine.name,
            update = set(CuisineCollection::isDeleted setTo false),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )
        return if (updatedCuisine != null) {
            updatedCuisine.toEntity()
        } else {
            container.cuisineCollection.insertOne(addedCuisine)
            addedCuisine.toEntity()
        }
    }

    override suspend fun areCuisinesExist(cuisineIds: List<String>): Boolean {
        val cuisines = container.cuisineCollection.find(
            and(
                CuisineCollection::id `in` cuisineIds.toObjectIds(),
                CuisineCollection::isDeleted eq false
            )
        ).toList()

        return cuisines.size == cuisineIds.size
    }

    override suspend fun updateCuisine(cuisine: Cuisine): Cuisine {
        return container.cuisineCollection.findOneAndUpdate(
            CuisineCollection::id eq ObjectId(cuisine.id),
            cuisine.toCollection(),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )?.toEntity() ?: throw MultiErrorException(listOf(NOT_FOUND))
    }

    override suspend fun deleteCuisine(id: String): Boolean =
        container.cuisineCollection.updateOne(
            filter = CuisineCollection::id eq ObjectId(id),
            update = set(CuisineCollection::isDeleted setTo true),
        ).isSuccessfullyUpdated()

    override suspend fun getCuisineByName(cuisineName: String): Cuisine? {
        return container.cuisineCollection.findOne(
            and(
                CuisineCollection::name eq cuisineName,
                CuisineCollection::isDeleted eq false
            )
        )?.toEntity()
    }

    //endregion

    //region Order
    override suspend fun addOrder(order: Order): Boolean {
        return container.orderCollection.insertOne(order.toCollection()).wasAcknowledged()
    }

    override suspend fun getOrdersByRestaurantId(restaurantId: String): List<Order> {
        return container.orderCollection.find(
            OrderCollection::restaurantId
                    eq ObjectId(restaurantId)
        ).toList().toEntity()
    }

    override suspend fun getActiveOrdersByRestaurantId(restaurantId: String): List<Order> {
        return container.orderCollection.find(
            OrderCollection::restaurantId eq ObjectId(restaurantId),
            OrderCollection::orderStatus ne Order.Status.CANCELED.statusCode,
            OrderCollection::orderStatus ne Order.Status.DONE.statusCode
        ).toList().toEntity()
    }

    override suspend fun getOrderById(orderId: String): Order? =
        container.orderCollection.findOneById(ObjectId(orderId))?.toEntity()

    override suspend fun updateOrderStatus(orderId: String, status: Order.Status): Order? {
        val updateOperation = setValue(OrderCollection::orderStatus, status.statusCode)
        val updatedOrder = container.orderCollection.findOneAndUpdate(
            filter = OrderCollection::id eq ObjectId(orderId),
            update = updateOperation
        )
        return updatedOrder?.toEntity()
    }

    override suspend fun getOrdersHistory(restaurantId: String, page: Int, limit: Int): List<Order> {
        return container.orderCollection
            .find(
                OrderCollection::orderStatus eq Order.Status.DONE.statusCode,
                OrderCollection::orderStatus eq Order.Status.CANCELED.statusCode,
                OrderCollection::restaurantId eq ObjectId(restaurantId)
            )
            .sort(descending(OrderCollection::createdAt))
            .paginate(page, limit).toList().toEntity()
    }
    //endregion

}