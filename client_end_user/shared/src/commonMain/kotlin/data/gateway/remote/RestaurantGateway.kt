package data.gateway.remote

import data.remote.mapper.toCuisineEntity
import data.remote.mapper.toEntity
import data.remote.model.CuisineDto
import data.remote.model.MealDto
import data.remote.model.MealRestaurantDto
import data.remote.model.OfferDto
import data.remote.model.RestaurantDto
import data.remote.model.ServerResponse
import domain.entity.Cuisine
import domain.entity.Meal
import domain.entity.Offer
import domain.entity.Restaurant
import domain.gateway.IRestaurantGateway
import domain.utils.GeneralException
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RestaurantGateway(client: HttpClient) : BaseGateway(client = client), IRestaurantGateway {
    override suspend fun getCuisines(): List<Cuisine> {
        return tryToExecute<ServerResponse<List<CuisineDto>>> {
            get("/cuisines")
        }.value?.toCuisineEntity() ?: throw GeneralException.NotFoundException
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

    override suspend fun getMealsInCuisine(cuisineId: String): List<Meal> {
        return tryToExecute<ServerResponse<List<MealDto>>> {
            get("/cuisine/$cuisineId/meals")
        }.value?.toEntity() ?: throw GeneralException.NotFoundException
    }
}
