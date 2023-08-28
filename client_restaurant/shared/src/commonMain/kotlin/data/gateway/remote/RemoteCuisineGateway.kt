package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.model.CuisineDto
import domain.entity.Cuisine
import domain.gateway.remote.IRemoteCuisineGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.get


class RemoteCuisineGateway(client: HttpClient) : IRemoteCuisineGateway,
    BaseRemoteGateway(client = client) {

    override suspend fun getCuisinesByRestaurantId(restaurantId: String): List<Cuisine> {
        return tryToExecute<List<CuisineDto>> {
            get("/cuisines/$restaurantId")
        }.toEntity()
    }
}