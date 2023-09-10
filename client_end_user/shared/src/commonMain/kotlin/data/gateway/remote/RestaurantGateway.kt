package data.gateway.remote

import data.remote.mapper.toCuisineEntity
import data.remote.model.CuisineDto
import data.remote.model.ServerResponse
import domain.entity.Cuisine
import domain.entity.InProgressWrapper
import domain.entity.Order
import domain.entity.Taxi
import domain.entity.Trip
import domain.gateway.IRestaurantRemoteGateway
import domain.utils.NotFoundException
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RestaurantGateway(client: HttpClient) : BaseGateway(client = client),
    IRestaurantRemoteGateway {
    override suspend fun getCuisines(): List<Cuisine> {
        return tryToExecute<ServerResponse<List<CuisineDto>>> {
            get("/cuisines")
        }.value?.toCuisineEntity() ?: throw NotFoundException()
    }

    // region Fake implementation todo: need to use real api
    override suspend fun getInProgress(): InProgressWrapper {
        return InProgressWrapper(
            taxisOnTheWay = getTaxiOnTheWay(),
            tripsOnTheWay = getActiveRide(),
            ordersOnTheWay = getActiveOrder(),
        )
    }

    private fun getActiveRide(): List<Trip> {
        return listOf(
            Trip(
                id = "khhfhdfhd",
                timeToArriveInMints = 30,
            )
        )
    }

    private fun getTaxiOnTheWay(): List<Taxi> {
        return listOf(
            Taxi(
                id = "khhfhdfhd",
                color = "White",
                plate = "A)1234BC",
                timeToArriveInMints = 30,
            )
        )
    }

    private fun getActiveOrder(): List<Order> {
        return listOf(
            Order(
                id = "khhfhdfhd",
                restaurantName = "burger king",
            )
        )
    }
    // endregion
}