package domain.gateway

import data.remote.model.MealRestaurantDto
import domain.entity.Cuisine
import domain.entity.Explore
import domain.entity.InProgressWrapper
import domain.entity.Meal
import domain.entity.Restaurant
import io.realm.kotlin.query.RealmQuery

interface IRestaurantRemoteGateway {
    suspend fun getCuisines(): List<Cuisine>
    suspend fun getInProgress(): InProgressWrapper

    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun addRestaurantToFavorites(restaurantId: String): Boolean
    suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean

    suspend fun search(query: String): Explore
}
