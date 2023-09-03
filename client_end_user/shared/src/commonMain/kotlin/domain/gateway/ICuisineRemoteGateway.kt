package domain.gateway

import domain.entity.Cuisine

interface ICuisineRemoteGateway {
    suspend fun getCuisines(): List<Cuisine>

}