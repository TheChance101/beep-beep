package data.gateway.remote

import data.remote.mapper.toCuisineEntity
import data.remote.model.CuisineDto
import data.remote.model.ServerResponse
import domain.entity.Cuisine
import domain.gateway.IRestaurantRemoteGateway
import domain.utils.NotFoundException
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RestaurantGateway(client: HttpClient) : BaseGateway(client = client), IRestaurantRemoteGateway {
    override suspend fun getCuisines(): List<Cuisine> {
        return tryToExecute<ServerResponse<List<CuisineDto>>> {
            get("/cuisines")
        }.value?.toCuisineEntity() ?: throw NotFoundException()
    }
}