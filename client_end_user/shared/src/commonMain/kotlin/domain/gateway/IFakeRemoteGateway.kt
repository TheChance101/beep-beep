package domain.gateway

import domain.entity.Restaurant
import domain.entity.Offer

interface IFakeRemoteGateway {
    suspend fun getFavoriteRestaurants(): List<Restaurant>
    fun getNewOffers(): List<Offer>
}