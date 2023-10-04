package domain.gateway

import domain.entity.Cart
import domain.entity.Meal
import domain.entity.Notification
import domain.entity.Offer
import domain.entity.Restaurant
import domain.entity.User

interface IFakeRemoteGateway {
    suspend fun getFavoriteRestaurants(): List<Restaurant>

    suspend fun getUsrWallet(): User
    fun getNewOffers(): List<Offer>
    suspend fun getNotificationHistory(): List<Notification>
    suspend fun getAllCartMeals(): Cart

    suspend fun getMostOrdersMeal(restaurantId: String): List<Meal>

    suspend fun getSweets(restaurantId: String): List<Meal>

    suspend fun getMealById(mealId: String): Meal

}
