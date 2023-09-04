package domain.gateway

import domain.entity.Restaurant

interface IFakeRemoteGateway {
    suspend fun getFavoriteRestaurants(): List<Restaurant>
}