package data.gateway.remote

import data.remote.mapper.toDto
import data.remote.mapper.toEntity
import data.remote.model.BaseResponse
import data.remote.model.RestaurantDto
import domain.entity.Restaurant
import domain.gateway.remote.IRestaurantRemoteGateway
import io.ktor.client.*
import io.ktor.client.request.*
import presentation.base.NotFoundedException


class RestaurantRemoteGateWay(client: HttpClient) : BaseRemoteGateway(client),
    IRestaurantRemoteGateway {

    override suspend fun getRestaurantsByOwnerId(ownerId: String): List<Restaurant> {
        return emptyList()
    }

    override suspend fun updateRestaurantInfo(restaurant: Restaurant): Boolean {
        return tryToExecute<BaseResponse<Boolean>> {
            post("/restaurant") { setBody(restaurant.toDto()) }
        }.value ?: false
    }

    override suspend fun getRestaurantInfo(restaurantId: String): Restaurant {
        return tryToExecute<BaseResponse<RestaurantDto>> {
            get("/restaurant/$restaurantId")
        }.value?.toEntity() ?: throw NotFoundedException()
    }

}