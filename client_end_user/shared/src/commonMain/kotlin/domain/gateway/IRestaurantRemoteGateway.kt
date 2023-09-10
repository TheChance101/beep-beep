package domain.gateway

import domain.entity.Cuisine
import domain.entity.InProgressWrapper

interface IRestaurantRemoteGateway {
    suspend fun getCuisines(): List<Cuisine>
    suspend fun getInProgress(): InProgressWrapper
}