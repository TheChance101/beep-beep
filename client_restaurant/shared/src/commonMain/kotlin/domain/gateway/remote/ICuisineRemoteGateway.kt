package domain.gateway.remote

import domain.entity.Cuisine


interface ICuisineRemoteGateway {
    suspend fun getCuisines(): List<Cuisine>

}