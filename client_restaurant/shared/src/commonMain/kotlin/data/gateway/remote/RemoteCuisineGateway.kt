package data.gateway.remote

import domain.entity.Cuisine
import domain.gateway.remote.IRemoteCuisineGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter


class RemoteCuisineGateway(client: HttpClient) : IRemoteCuisineGateway,
    BaseRemoteGateway(client = client) {

    override suspend fun getCuisinesByRestaurantId(restaurantId: String): List<Cuisine> {
        return tryToExecute<List<Cuisine>> {
            get("/cuisines") {
                parameter("page", 1)
//                parameter("limit", limit)
//                parameter("searchTerm", searchTerm)
            }
        }
    }
}