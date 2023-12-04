package data.gateway.local

import data.local.mapper.toFavoriteRestaurant
import data.local.mapper.toRestaurantCollection
import data.local.model.RestaurantCollection
import domain.entity.Restaurant
import domain.gateway.local.ILocalRestaurantGateway
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.find

class LocalRestaurantGateway(private val realm: Realm) : ILocalRestaurantGateway {
    override suspend fun addRestaurantToFavorites(vararg restaurant: Restaurant): Boolean {
        val restaurantCollectionList = restaurant.map { it.toRestaurantCollection() }
        realm.write {
            restaurantCollectionList.forEach {
                copyToRealm(it, updatePolicy = UpdatePolicy.ALL)
            }
        }
        return true
    }

    override suspend fun getFavoriteRestaurants(): List<Restaurant> {
        return realm.query<RestaurantCollection>().find().map { it.toFavoriteRestaurant() }
    }

    override suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean {
        realm.write {
            val restaurantToDelete =
                realm.query<RestaurantCollection>("$ID == '$restaurantId'").find().first()
            val liveRestaurant = findLatest(restaurantToDelete)
            if (liveRestaurant != null) {
                delete(liveRestaurant)
            }
        }
        return true
    }
    override suspend fun clearFavoriteRestaurants(): Boolean {
        val restaurants = realm.query<RestaurantCollection>().find()
            realm.write {
                restaurants.forEach { restaurant ->
                    val liveRestaurant = findLatest(restaurant)
                    if (liveRestaurant != null) {
                        delete(liveRestaurant)
                    }
                }
            }
        return restaurants.isEmpty()
    }


    private companion object {
        private const val ID = "id"
    }

}
