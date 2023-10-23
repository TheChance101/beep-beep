package data.gateway.remote

import data.remote.mapper.toCuisineEntity
import data.remote.mapper.toEntity
import data.remote.model.CuisineDto
import data.remote.model.MealDto
import data.remote.model.MealRestaurantDto
import data.remote.model.OfferDto
import data.remote.model.PaginationResponse
import data.remote.model.RestaurantDto
import data.remote.model.ServerResponse
import domain.entity.Cuisine
import domain.entity.InProgressWrapper
import domain.entity.Location
import domain.entity.Meal
import domain.entity.Offer
import domain.entity.Order
import domain.entity.Price
import domain.entity.Restaurant
import domain.entity.Taxi
import domain.entity.Trip
import domain.gateway.IRestaurantGateway
import domain.utils.GeneralException
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlin.random.Random

class RestaurantGateway(client: HttpClient) : BaseGateway(client = client), IRestaurantGateway {
    override suspend fun getCuisines(): List<Cuisine> {
        return tryToExecute<ServerResponse<List<CuisineDto>>> {
            get("/cuisines")
        }.value?.toCuisineEntity() ?: throw GeneralException.NotFoundException
    }

    override suspend fun getInProgress(): InProgressWrapper {
        // todo: implement this when the backend is ready
        return InProgressWrapper(
            taxisOnTheWay = getTaxiOnTheWay(),
            tripsOnTheWay = getActiveRide(),
            ordersOnTheWay = emptyList(),
        )
    }

    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        return tryToExecute<ServerResponse<RestaurantDto>> {
            get("/restaurant/$restaurantId")
        }.value?.toEntity() ?: throw GeneralException.NotFoundException
    }

    override suspend fun getMealById(mealId: String): Meal {
        return tryToExecute<ServerResponse<MealDto>> {
            get("/meal/$mealId")
        }.value?.toEntity() ?: throw GeneralException.NotFoundException
    }

    override suspend fun getNewOffers(): List<Offer> {
        return tryToExecute<ServerResponse<List<OfferDto>>> {
            get("/offers/restaurants")
        }.value?.toEntity() ?: throw GeneralException.NotFoundException
    }

    override suspend fun getMostOrdersMeal(restaurantId: String): List<Meal> {
        // todo: implement this when the backend is ready
        return emptyList()
    }

    override suspend fun getCuisinesWithMealsInRestaurant(restaurantId: String): List<Cuisine> {
        return tryToExecute<ServerResponse<List<CuisineDto>>> {
            get("/restaurant/${restaurantId}/cuisineMeals")
        }.value?.toCuisineEntity() ?: throw GeneralException.NotFoundException
    }


    override suspend fun search(query: String): Pair<List<Restaurant>, List<Meal>> {
        val result = tryToExecute<ServerResponse<MealRestaurantDto>> {
            get("/restaurants/search?query=$query")
        }.value ?: throw GeneralException.NotFoundException
        return Pair(result.restaurants.toEntity(), result.meals.toEntity())
    }

    override suspend fun getMealsInCuisine(cuisineId: String, page: Int, limit: Int): List<Meal> {
        return tryToExecute<ServerResponse<PaginationResponse<MealDto>>> {
            get("/cuisine/$cuisineId/meals") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }.value?.items?.map { it.toEntity() } ?: throw GeneralException.NotFoundException
    }


    // all the following methods are fake data and should be removed when the backend is ready
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
                price = Price(20.0, "$"),
                startDate = LocalDate(2023, 9, 20),
                endDate = LocalDate(2023, 9, 20),
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


}
