package data.gateway.remote

import domain.entity.Cuisine
import domain.entity.Meal
import domain.gateway.remote.IRemoteCuisineGateway
import io.ktor.client.HttpClient


class RemoteCuisineGateway(private val client: HttpClient) : IRemoteCuisineGateway {

    override suspend fun getCuisinesByRestaurantId(restaurantId: String): List<Cuisine> {
       return emptyList()
    }
}