package data.gateway.remote

import data.remote.mapper.toCuisineEntity
import data.remote.model.CuisineDto
import data.remote.model.ServerResponse
import domain.entity.Cuisine
import domain.entity.InProgressWrapper
import domain.entity.Location
import domain.entity.Order
import domain.entity.Taxi
import domain.entity.Trip
import domain.gateway.IRestaurantRemoteGateway
import domain.utils.GeneralException
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.datetime.Clock

class RestaurantGateway(client: HttpClient) : BaseGateway(client = client),
    IRestaurantRemoteGateway {
    override suspend fun getCuisines(): List<Cuisine> {
        return tryToExecute<ServerResponse<List<CuisineDto>>> {
            get("/cuisines")
        }.value?.toCuisineEntity() ?: throw GeneralException.NotFoundException
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
                taxiId = "taxi123",
                taxiPlateNumber = "ABC123",
                driverId = "driver456",
                driverName = "Ali Yasein",
                clientId = "client789",
                startPoint = Location(37.7749, -122.4194),
                destination = Location(37.7831, -122.4039),
                rate = 4.5,
                price = 25.0,
                startDate = "2023-09-20 09:00:00",
                endDate = "2023-09-20 09:30:00",
                timeToArriveInMints = 30
            )
        )
    }


    private fun getTaxiOnTheWay(): List<Taxi> {
        return listOf(
            Taxi(
                id = "khhfhdfhd",
                color = "White",
                plate = "1234BC",
                timeToArriveInMints = 30,
            )
        )
    }

    private fun getActiveOrder(): List<Order> {
        return listOf(
            Order(
                id = "khhfhdfhd",
                userId = "user123",
                restaurantId = "restaurant456",
                restaurantName = "Hamada Market",
                restaurantImageUrl = "",
                meals = listOf(
                    Order.Meal(
                        mealId = "meal789",
                        mealName = "Cheeseburger",
                        quantity = 2
                    ),
                    Order.Meal(
                        mealId = "meal101",
                        mealName = "Fries",
                        quantity = 1
                    )
                ),
                totalPrice = 15.99,
                createdAt = Clock.System.now().epochSeconds,
                orderStatus = 1,
                timeToArriveInMints = 20
            )
        )
    }

    // endregion
}