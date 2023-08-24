package data.gateway

import data.remote.model.TokenDto
import domain.entity.Category
import domain.entity.Meal
import domain.entity.Order
import domain.entity.Restaurant
import domain.gateway.IRemoteGateWay
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class RestaurantGateWay(
    private val realm: Realm
) : IRemoteGateWay {

    override suspend fun saveAccessToken(token: String) {

    }
    override suspend fun getAccessToken(): String {
        return ""
    }
    override suspend fun saveRefreshToken(token: String) {
        realm.write {
            copyToRealm(TokenDto().apply {
                this.refreshToken = token
            })
        }
    }

    override suspend fun getRefreshToken(): String {
        return realm.query<TokenDto>().first().find()?.refreshToken
            ?: throw Exception("Refresh Token not found")
    }

    //region restaurant
    override suspend fun getRestaurantsByOwnerId(ownerId: String): List<Restaurant> {
        return emptyList()
    }

    override suspend fun updateRestaurantInfo(restaurant: Restaurant): Restaurant? {
        return restaurant
    }

    override suspend fun getRestaurantInfo(restaurantId: String): Restaurant? {
        return getRestaurantsByOwnerId("7bf7ef77d907").find { it.id == restaurantId }
    }
    //endregion restaurant

    //region meal
    override suspend fun getMealsByRestaurantId(restaurantId: String): List<Meal> {
        return emptyList()
    }

    override suspend fun getMealById(mealId: String): Meal? {
        return getMealsByRestaurantId("ef77d90").find { it.id == mealId }
    }

    override suspend fun addMeal(meal: Meal): Meal {
        return meal
    }

    override suspend fun updateMeal(meal: Meal): Meal {
        return meal
    }
    //endregion meal

    //region order
    override suspend fun getCurrentOrders(restaurantId: String): List<Order> {
        return emptyList()
    }

    override suspend fun getOrdersHistory(restaurantId: String): List<Order> {
        return emptyList()
    }

    override suspend fun updateOrderState(orderId: String, orderState: Int): Order? {

        return null
    }
    //endregion order

    //region category
    override suspend fun getCategoriesByRestaurantId(restaurantId: String): Category {
        return Category(id = "8a-4854-49c6-99ed-ef09899c2", name = "Sea Food")
    }
    //endregion category

}