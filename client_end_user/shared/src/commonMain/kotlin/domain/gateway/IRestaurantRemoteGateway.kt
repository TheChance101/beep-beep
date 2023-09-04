package domain.gateway

import domain.entity.Cuisine

interface IRestaurantRemoteGateway {
    suspend fun getCuisines(): List<Cuisine>

}