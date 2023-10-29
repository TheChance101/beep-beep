package data.gateway.fake

import data.remote.mapper.toEntity
import data.remote.model.LocationDto
import data.remote.model.RestaurantDto
import domain.entity.Restaurant
import domain.entity.Session
import domain.entity.Account
import domain.entity.Address
import domain.entity.Price
import domain.entity.User
import domain.gateway.IUserGateway

class FakeUserGateway : IUserGateway {
    override suspend fun createUser(account: Account): User {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser(username: String, password: String): Session {
        TODO("Not yet implemented")
    }

    override suspend fun refreshAccessToken(refreshToken: String): Pair<String, String> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserProfile(): User {
        TODO("Not yet implemented")
    }

    override suspend fun getUserAddresses(): List<Address> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfile(fullName: String?, phone: String?): User {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteRestaurants(): List<Restaurant> {
        return restaurants.map { it.toEntity() }
    }

    override suspend fun addRestaurantToFavorites(restaurantId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean {
        TODO("Not yet implemented")
    }

    private val restaurants = listOf(
        RestaurantDto(
            id = "64f372095fecc11e6d917656",
            ownerId = "64f3663e5ddbc15bfd1efcfa",
            ownerUsername = "hamada",
            name = "Hamada Market",
            rate = 4.5,
            priceLevel = "$$",
            phone = "1234567890",
            description = "This is description",
            closingTime = "07:30",
            openingTime = "22:00",
            location = LocationDto(22.0, 10.5),
            address = "Main street, 123"
        ),
        RestaurantDto(
            id = "64f373b35fecc11e6d917659",
            ownerId = "64f3663e5ddbc15bfd1efcfa",
            ownerUsername = "masala",
            name = "Masala Restaurant",
            rate = 3.5,
            priceLevel = "$",
            phone = "1234567890",
            description = "This is description",
            closingTime = "09:00",
            openingTime = "20:00",
            location = LocationDto(12.0, 10.5),
            address = "New street, 23"
        ),
        RestaurantDto(
            id = "64f373be5fecc11e6d91765a",
            ownerId = "64f3663e5ddbc15bfd1efcfa",
            ownerUsername = "the_chance",
            name = "Chance Market",
            rate = 5.0,
            priceLevel = "$$$",
            phone = "1234567890",
            description = "This is description",
            closingTime = "08:30",
            openingTime = "17:00",
            location = LocationDto(33.0, 22.5),
            address = "Cairo street, 101"
        )
    )
}
