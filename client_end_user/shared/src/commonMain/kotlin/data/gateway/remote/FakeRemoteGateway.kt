package data.gateway.remote

import domain.entity.Location
import domain.entity.PriceLevel
import domain.entity.Restaurant
import domain.entity.Time
import domain.gateway.IFakeRemoteGateway

class FakeRemoteGateway : IFakeRemoteGateway {

    override suspend fun getFavoriteRestaurants(): List<Restaurant> {
        return restaurants
    }

    private val restaurants = listOf(
        Restaurant(
            id = "64f372095fecc11e6d917656",
            ownerId = "64f3663e5ddbc15bfd1efcfa",
            ownerUsername = "hamada",
            name = "Hamada Market",
            rate = 4.5,
            priceLevel = PriceLevel.HIGH,
            phone = "1234567890",
            description = "This is description",
            closingTime = Time(7, 30),
            openingTime = Time(22, 0),
            location = Location(22.0, 10.5),
            address = "Main street, 123"
        ),
        Restaurant(
            id = "64f373b35fecc11e6d917659",
            ownerId = "64f3663e5ddbc15bfd1efcfa",
            ownerUsername = "masala",
            name = "Masala Restaurant",
            rate = 3.5,
            priceLevel = PriceLevel.MEDIUM,
            phone = "1234567890",
            description = "This is description",
            closingTime = Time(9, 0),
            openingTime = Time(20, 0),
            location = Location(12.0, 10.5),
            address = "New street, 23"
        ),
        Restaurant(
            id = "64f373be5fecc11e6d91765a",
            ownerId = "64f3663e5ddbc15bfd1efcfa",
            ownerUsername = "the_chance",
            name = "Chance Market",
            rate = 5.0,
            priceLevel = PriceLevel.HIGH,
            phone = "1234567890",
            description = "This is description",
            closingTime = Time(8, 30),
            openingTime = Time(17, 0),
            location = Location(33.0, 22.5),
            address = "Cairo street, 101"
        )
    )
}