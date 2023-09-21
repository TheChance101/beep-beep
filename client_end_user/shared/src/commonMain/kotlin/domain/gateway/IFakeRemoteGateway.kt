package domain.gateway

import domain.entity.Notification
import domain.entity.Restaurant
import domain.entity.Offer
import domain.entity.User
import domain.entity.UserDetails

interface IFakeRemoteGateway {
    suspend fun getFavoriteRestaurants(): List<Restaurant>

    suspend fun getUsrWallet():User
    fun getNewOffers(): List<Offer>
    suspend fun getNotificationHistory(): List<Notification>

    suspend fun getUserProfile(): UserDetails
}
