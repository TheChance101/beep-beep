package data.gateway.remote

import data.remote.mapper.toCuisineEntity
import data.remote.model.CuisineDto
import data.remote.model.ServerResponse
import domain.entity.Cuisine
import domain.entity.Order
import domain.entity.Taxi
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

    // region Fake implementation
    override suspend fun getTaxiOnTheWay(): Taxi? {
        return Taxi(
            id = "khhfhdfhd",
            color = "White",
            plate = "A1234BC",
            timeToArriveInMints = 30,
        )
    }

    override suspend fun getActiveRide(): Taxi? {
        return Taxi(
            id = "khhfhdfhd",
            color = "White",
            plate = "A1234BC",
            timeToArriveInMints = 30,
        )
    }

    override suspend fun getActiveOrder(): Order? {
        return Order(
            id = "khhfhdfhd",
            restaurantName = "burger king",
        )
    }
    // endregion
}