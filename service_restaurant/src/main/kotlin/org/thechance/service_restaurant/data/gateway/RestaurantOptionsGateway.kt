package org.thechance.service_restaurant.data.gateway

import com.mongodb.client.model.Updates
import org.litote.kmongo.addToSet
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.aggregate
import org.litote.kmongo.eq
import org.litote.kmongo.`in`
import org.litote.kmongo.lookup
import org.litote.kmongo.match
import org.litote.kmongo.project
import org.litote.kmongo.pull
import org.litote.kmongo.pullAll
import org.litote.kmongo.set
import org.litote.kmongo.setTo
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.CategoryCollection
import org.thechance.service_restaurant.data.collection.CuisineCollection
import org.thechance.service_restaurant.data.collection.MealCollection
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.collection.relationModels.CategoryRestaurant
import org.thechance.service_restaurant.data.collection.relationModels.MealCuisines
import org.thechance.service_restaurant.data.utils.isSuccessfullyUpdated
import org.thechance.service_restaurant.data.utils.paginate
import org.thechance.service_restaurant.data.utils.toUUIDs
import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND
import java.util.*

class RestaurantOptionsGateway(private val container: DataBaseContainer) :
    IRestaurantOptionsGateway {

    //region Category
    override suspend fun getCategories(page: Int, limit: Int): List<Category> {
        return container.categoryCollection.find(CategoryCollection::isDeleted eq false)
            .paginate(page, limit).toList().toEntity()
    }

    override suspend fun getCategory(categoryId: String): Category? {
        return container.categoryCollection.aggregate<CategoryCollection>(
            match(
                and(
                    CategoryCollection::id eq UUID.fromString(categoryId),
                    CategoryCollection::isDeleted eq false
                )
            ),
            project(CategoryCollection::name, CategoryCollection::id)
        ).toList().firstOrNull()?.toEntity()
    }

    override suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant> {
        return container.categoryCollection.aggregate<CategoryRestaurant>(
            match(CategoryCollection::id eq UUID.fromString(categoryId)),
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
                    CategoryCollection::id `in` categoryIds.toUUIDs(),
                    CategoryCollection::isDeleted eq false
                )
            ).toList()
        return categoryObjects.size == categoryIds.size

    }

    override suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category> {
        return container.restaurantCollection.aggregate<CategoryRestaurant>(
            match(RestaurantCollection::id eq UUID.fromString(restaurantId)),
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
            CategoryCollection::id `in` categoryIds.toUUIDs(),
            addToSet(CategoryCollection::restaurantIds, UUID.fromString(restaurantId))
        ).isSuccessfullyUpdated()

        val resultAddToRestaurant = container.restaurantCollection.updateOneById(
            UUID.fromString(restaurantId),
            update = Updates.addEachToSet(
                RestaurantCollection::categoryIds.name,
                categoryIds.toUUIDs()
            )
        ).isSuccessfullyUpdated()

        return resultAddToCategory and resultAddToRestaurant
    }

    override suspend fun updateCategory(category: Category): Category {
        return container.categoryCollection.findOneAndUpdate(
            filter = CategoryCollection::id eq UUID.fromString(category.id),
            update = category.toCollection(),
        )?.toEntity() ?: throw MultiErrorException(listOf(NOT_FOUND))
    }

    override suspend fun deleteCategory(categoryId: String): Boolean {
        return container.categoryCollection.updateOneById(
            id = UUID.fromString(categoryId),
            update = Updates.set(CategoryCollection::isDeleted.name, true),
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteRestaurantsInCategory(
        categoryId: String,
        restaurantIds: List<String>
    ): Boolean {
        val resultDeleteFromRestaurant = container.restaurantCollection.updateMany(
            RestaurantCollection::id `in` restaurantIds.toUUIDs(),
            pull(RestaurantCollection::categoryIds, UUID.fromString(categoryId))
        ).isSuccessfullyUpdated()

        val resultDeleteFromCategory = container.categoryCollection.updateOneById(
            UUID.fromString(categoryId),
            pullAll(CategoryCollection::restaurantIds, restaurantIds.toUUIDs())
        ).isSuccessfullyUpdated()
        return resultDeleteFromRestaurant and resultDeleteFromCategory
    }
    //endregion

    //region Cuisines
    override suspend fun getCuisines(): List<Cuisine> =
        container.cuisineCollection.find(MealCollection::isDeleted eq false).toList().toEntity()

    override suspend fun getCuisineById(id: String): Cuisine? =
        container.cuisineCollection.findOneById(UUID.fromString(id))?.takeIf { !it.isDeleted }?.toEntity()

    override suspend fun getMealsInCuisine(cuisineId: String): List<Meal> {
        return container.cuisineCollection.aggregate<MealCuisines>(
            match(CuisineCollection::id eq UUID.fromString(cuisineId)),
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
        container.cuisineCollection.insertOne(addedCuisine)
        return addedCuisine.toEntity()
    }

    override suspend fun areCuisinesExist(cuisineIds: List<String>): Boolean {
        val cuisines = container.cuisineCollection.find(
            and(
                CuisineCollection::id `in` cuisineIds.toUUIDs(),
                CuisineCollection::isDeleted eq false
            )
        ).toList()

        return cuisines.size == cuisineIds.size
    }

    override suspend fun updateCuisine(cuisine: Cuisine): Cuisine {
        return container.cuisineCollection.findOneAndUpdate(
            CuisineCollection::id eq UUID.fromString(cuisine.id),
            cuisine.toCollection(),
        )?.toEntity() ?: throw MultiErrorException(listOf(NOT_FOUND))
    }

    override suspend fun deleteCuisine(id: String): Boolean =
        container.cuisineCollection.updateOne(
            filter = CuisineCollection::id eq UUID.fromString(id),
            update = set(CuisineCollection::isDeleted setTo true),
        ).isSuccessfullyUpdated()

    //endregion

}