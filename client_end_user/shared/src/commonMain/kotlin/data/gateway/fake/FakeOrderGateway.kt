package data.gateway.fake

import data.remote.mapper.toEntity
import data.remote.model.CartDto
import data.remote.model.CartMealDto
import domain.entity.Cart
import domain.entity.Location
import domain.entity.Order
import domain.entity.Price
import domain.entity.Trip
import domain.gateway.ITransactionsGateway
import kotlinx.datetime.LocalDate

class FakeOrderGateway : ITransactionsGateway {
    override suspend fun getTripHistory(): List<Trip> {
        return listOf(
            Trip(
                id = "trip1",
                taxiId = "taxi123",
                taxiPlateNumber = "ABC123",
                driverId = "driver456",
                driverName = "Said Kolish",
                clientId = "client789",
                startPoint = Location(37.7749, -122.4194),
                destination = Location(37.7831, -122.4039),
                rate = 4.5,
                price = Price(20.0, "$"),
                startDate = LocalDate(2023, 9, 20),
                endDate = LocalDate(2023, 9, 20),
                timeToArriveInMints = 30
            ),
            Trip(
                id = "trip2",
                taxiId = "taxi789",
                taxiPlateNumber = "XYZ456",
                driverId = "driver789",
                driverName = "Mohamed Ali",
                clientId = "client101",
                startPoint = Location(37.7802, -122.4212),
                destination = Location(37.7915, -122.4017),
                rate = 4.2,
                price = Price(20.0, "$"),
                startDate = LocalDate(2023, 9, 20),
                endDate = LocalDate(2023, 9, 20),
                timeToArriveInMints = 25
            ),
            Trip(
                id = "trip2",
                taxiId = "taxi789",
                taxiPlateNumber = "XYZ456",
                driverId = "driver789",
                driverName = "Said Hafez",
                clientId = "client101",
                startPoint = Location(37.7802, -122.4212),
                destination = Location(37.7915, -122.4017),
                rate = 4.2,
                price = Price(20.0, "$"),
                startDate = LocalDate(2023, 9, 20),
                endDate = LocalDate(2023, 9, 20),
                timeToArriveInMints = 25
            ),
        )
    }

    override suspend fun getOrderHistoryGateway(): List<Order> {
        return listOf(
            Order(
                id = "order1",
                restaurantId = "restaurant456",
                restaurantName = "Burger king",
                restaurantImageUrl = "https://thecarefreekitchen.com/wp-content/uploads/2021/10/Buffalo-Chicken-Taquitos-1024x1024.jpg",
                meals = listOf(),
                price = Price(20.0, "$"),
                createdAt = 5489897989474,
                orderStatus = 4,
            ),
            Order(
                id = "order2",
                restaurantId = "restaurant789",
                restaurantImageUrl = "https://www.freshnlean.com/wp-content/uploads/2021/03/Meal-Plan-plate-protein.png",
                restaurantName = "Zeko Talawoth",
                meals = listOf(),
                price = Price(20.0, "$"),
                createdAt = 564844874874,
                orderStatus = 4,
            ),
            Order(
                id = "order1",
                restaurantId = "restaurant456",
                restaurantName = "Kebdaki",
                restaurantImageUrl = "https://www.foodandwine.com/thmb/bRz199ONebY-5h5gcvpOcHRxAkA=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Web_4000-Trifecta-Chicken-Breast-Sweet-Potato-Mixed-Vegetable_04-72a24aaee5584c06a26451603daec5c9.jpg",
                meals = listOf(),
                price = Price(20.0, "$"),
                createdAt = 9685989874,
                orderStatus = 4,
            )
        )
    }

    override suspend fun getCart(): Cart {
        TODO("Not yet implemented")
    }

}
