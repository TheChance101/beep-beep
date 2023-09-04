package domain.usecase

import data.gateway.remote.FakeRemoteGateway
import domain.entity.Restaurant

interface IGetFavoriteRestaurantsUseCase {
    suspend operator fun invoke(): List<Restaurant>
}

class GetFavoriteRestaurantsUseCase(private val fakeGateway: FakeRemoteGateway) :
    IGetFavoriteRestaurantsUseCase {
    override suspend operator fun invoke(): List<Restaurant> {
        return fakeGateway.getFavoriteRestaurants()
    }

}