package data.gateway

import domain.entity.Location
import domain.entity.Restaurant
import domain.gateway.IRemoteRestaurantGateway
import io.ktor.client.HttpClient


class RemoteRestaurantGateWay(private val client: HttpClient) : IRemoteRestaurantGateway {

    override suspend fun getRestaurantsByOwnerId(ownerId: String): List<Restaurant> {
        return emptyList()
    }

    override suspend fun updateRestaurantInfo(restaurant: Restaurant): Boolean {
        return true
    }

    override suspend fun getRestaurantInfo(restaurantId: String): Restaurant {
        return getRestaurantsByOwnerId("7bf7ef77d907").find { it.id == restaurantId } ?:
        Restaurant(
            id  = "",
            ownerId = "",
            address = "",
            location = Location(0.0,0.0),
            phone = "",
            openingTime = "",
            closingTime = "",
            rate = 0.0,
            priceLevel = "",
            description = "",
            ownerUsername = "",
            name = ""
        )
    }

}