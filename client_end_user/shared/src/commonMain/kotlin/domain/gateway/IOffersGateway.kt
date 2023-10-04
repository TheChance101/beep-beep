package domain.gateway

import domain.entity.Meal
import domain.entity.Offer

interface IOffersGateway {
    fun getNewOffers(): List<Offer>
    suspend fun getMostOrdersMeal(restaurantId: String): List<Meal>
    suspend fun getSweets(restaurantId: String): List<Meal>
}