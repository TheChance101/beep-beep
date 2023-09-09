package domain.gateway

import domain.entity.Cuisine
import domain.entity.Order
import domain.entity.Taxi

interface IRestaurantRemoteGateway {
    suspend fun getCuisines(): List<Cuisine>
    suspend fun getTaxiOnTheWay(): Taxi?
    suspend fun getActiveRide(): Taxi?
    suspend fun getActiveOrder(): Order?
}