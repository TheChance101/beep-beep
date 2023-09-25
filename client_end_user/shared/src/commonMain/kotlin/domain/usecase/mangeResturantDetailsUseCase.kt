package domain.usecase

import domain.entity.Meal
import domain.entity.Restaurant
import domain.gateway.IFakeRemoteGateway
import domain.gateway.IRestaurantRemoteGateway

interface IMangeRestaurantDetailsUseCase {

    suspend fun getRestaurantDetails(restaurantId: String) : Restaurant

    suspend fun getRestaurantMostOrders(restaurantId: String) : List<Meal>

    suspend fun getRestaurantSweets(restaurantId: String) : List<Meal>
}

class MangeRestaurantDetailsUseCase(
    private val restaurantRemoteGateway: IRestaurantRemoteGateway,
    private val fakeRemoteGateway: IFakeRemoteGateway) : IMangeRestaurantDetailsUseCase{
    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        return restaurantRemoteGateway.getRestaurantDetails(restaurantId)
    }

    override suspend fun getRestaurantMostOrders(restaurantId: String): List<Meal> {
        return fakeRemoteGateway.getMostOrdersMeal(restaurantId)
    }

    override suspend fun getRestaurantSweets(restaurantId: String): List<Meal> {
        return fakeRemoteGateway.getSweets(restaurantId)
    }

}