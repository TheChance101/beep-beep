package domain.usecase

import domain.entity.Meal
import domain.entity.Restaurant
import domain.gateway.IFakeRemoteGateway

interface IMangeRestaurantDetailsUseCase {

    suspend fun getRestaurantDetails(restaurantId: String) : Restaurant

    suspend fun getRestaurantMostOrders(restaurantId: String) : List<Meal>

    suspend fun getRestaurantSweets(restaurantId: String) : List<Meal>
}

class MangeRestaurantDetailsUseCase(private val fakeRemoteGateway: IFakeRemoteGateway) : IMangeRestaurantDetailsUseCase{
    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        return fakeRemoteGateway.getFavoriteRestaurants().first { it.id == restaurantId }
    }

    override suspend fun getRestaurantMostOrders(restaurantId: String): List<Meal> {
        return fakeRemoteGateway.getMostOrdersMeal(restaurantId)
    }

    override suspend fun getRestaurantSweets(restaurantId: String): List<Meal> {
        return fakeRemoteGateway.getSweets(restaurantId)
    }

}