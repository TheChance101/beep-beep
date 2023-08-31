package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.model.BaseResponse
import data.remote.model.RestaurantDto
import domain.entity.Restaurant
import domain.gateway.remote.IRestaurantRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import presentation.base.NotFoundedException


class RestaurantRemoteGateWay(client: HttpClient) : BaseRemoteGateway(client),
    IRestaurantRemoteGateway {

    override suspend fun getRestaurantsByOwnerId(ownerId: String): List<Restaurant> {
        return emptyList()
    }

    override suspend fun updateRestaurantInfo(restaurant: Restaurant): Boolean {
        return true
    }

    override suspend fun getRestaurantInfo(restaurantId: String): Restaurant {
        return tryToExecute<BaseResponse<RestaurantDto>> {
            get("/restaurant/$restaurantId")
        }.value?.toEntity() ?: throw NotFoundedException()
    }

}