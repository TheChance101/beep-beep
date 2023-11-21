package domain.gateway

import domain.entity.Account
import domain.entity.Restaurant
import domain.entity.Session
import domain.entity.Address
import domain.entity.NotificationHistory
import domain.entity.PaginationItems
import domain.entity.User

interface IUserGateway {
    suspend fun createUser(account: Account): User
    suspend fun loginUser(username: String, password: String, deviceToken: String): Session
    suspend fun getDeviceToken(): String
    suspend fun refreshAccessToken(): Pair<String, String>
    suspend fun getUserProfile(): User
    suspend fun getUserAddresses(): List<Address>
    suspend fun updateProfile(fullName: String?, phone: String?): User
    suspend fun getFavoriteRestaurants(): List<Restaurant>
    suspend fun addRestaurantToFavorites(restaurantId: String): Boolean
    suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean
    suspend fun getNotificationHistory(page: Int, limit: Int): PaginationItems<NotificationHistory>
    suspend fun getNotificationHistoryInLast24Hours(): List<NotificationHistory>
}
