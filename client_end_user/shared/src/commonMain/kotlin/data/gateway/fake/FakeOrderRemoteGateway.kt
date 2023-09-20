package data.gateway.fake

import domain.entity.Location
import domain.entity.Order
import domain.entity.Trip
import domain.gateway.IOrderRemoteGateway
import kotlinx.datetime.Clock

class FakeOrderRemoteGateway : IOrderRemoteGateway {
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
                price = 25.0,
                startDate = "2023-09-20 09:00:00",
                endDate = "2023-09-20 09:30:00",
                timeToArriveInMints = 30
            ),
            Trip(
                id = "trip2",
                taxiId = "taxi789",
                taxiPlateNumber = "XYZ456",
                driverId = "driver789",
                driverName = "Ahmed Hassan",
                clientId = "client101",
                startPoint = Location(37.7802, -122.4212),
                destination = Location(37.7915, -122.4017),
                rate = 4.2,
                price = 20.0,
                startDate = "2023-09-20 10:00:00",
                endDate = "2023-09-20 10:25:00",
                timeToArriveInMints = 25
            )
        )
    }

    override suspend fun getOrderHistoryGateway(): List<Order> {
        return listOf(
            Order(
                id = "order1",
                userId = "user123",
                restaurantId = "restaurant456",
                restaurantName = "Burger king",
                restaurantImageUrl = "",
                meals = listOf(
                    Order.Meal(
                        mealId = "meal1",
                        mealName = "Cheeseburger",
                        quantity = 2
                    ),
                    Order.Meal(
                        mealId = "meal2",
                        mealName = "Fries",
                        quantity = 1
                    )
                ),
                totalPrice = 15.99,
                createdAt = Clock.System.now().epochSeconds,
                orderStatus = 4,
                timeToArriveInMints = 20
            ),
            Order(
                id = "order2",
                userId = "user456",
                restaurantId = "restaurant789",
                restaurantImageUrl = "",
                restaurantName = "Zeko Talawoth",
                meals = listOf(
                    Order.Meal(
                        mealId = "meal3",
                        mealName = "Pizza",
                        quantity = 1
                    ),
                    Order.Meal(
                        mealId = "meal4",
                        mealName = "Soda",
                        quantity = 2
                    )
                ),
                totalPrice = 22.50,
                createdAt = Clock.System.now().epochSeconds,
                orderStatus = 4,
                timeToArriveInMints = 15
            ),
            Order(
                id = "order1",
                userId = "user123",
                restaurantId = "restaurant456",
                restaurantName = "Kebdaki",
                restaurantImageUrl = "",
                meals = listOf(
                    Order.Meal(
                        mealId = "meal1",
                        mealName = "Koshary",
                        quantity = 2
                    ),
                    Order.Meal(
                        mealId = "meal2",
                        mealName = "Fries",
                        quantity = 1
                    )
                ),
                totalPrice = 15.99,
                createdAt = Clock.System.now().epochSeconds,
                orderStatus = 4,
                timeToArriveInMints = 20
            ),
            Order(
                id = "order1",
                userId = "user123",
                restaurantId = "restaurant456",
                restaurantName = "Elza3em",
                restaurantImageUrl = "",
                meals = listOf(
                    Order.Meal(
                        mealId = "meal1",
                        mealName = "Cheeseburger",
                        quantity = 2
                    ),
                    Order.Meal(
                        mealId = "meal2",
                        mealName = "pizza",
                        quantity = 3
                    )
                ),
                totalPrice = 15.99,
                createdAt = Clock.System.now().epochSeconds,
                orderStatus = 4,
                timeToArriveInMints = 20
            ),
            Order(
                id = "order1",
                userId = "user123",
                restaurantId = "restaurant456",
                restaurantName = "Burger king",
                restaurantImageUrl = "",
                meals = listOf(
                    Order.Meal(
                        mealId = "meal1",
                        mealName = "Cheeseburger",
                        quantity = 2
                    ),
                    Order.Meal(
                        mealId = "meal2",
                        mealName = "Fries",
                        quantity = 1
                    )
                ),
                totalPrice = 15.99,
                createdAt = Clock.System.now().epochSeconds,
                orderStatus = 4,
                timeToArriveInMints = 20
            ),
            Order(
                id = "order1",
                userId = "user123",
                restaurantId = "restaurant456",
                restaurantName = "Burger king",
                restaurantImageUrl = "",
                meals = listOf(
                    Order.Meal(
                        mealId = "meal1",
                        mealName = "Cheeseburger",
                        quantity = 2
                    ),
                    Order.Meal(
                        mealId = "meal2",
                        mealName = "Fries",
                        quantity = 1
                    )
                ),
                totalPrice = 15.99,
                createdAt = Clock.System.now().epochSeconds,
                orderStatus = 4,
                timeToArriveInMints = 20
            ),
            Order(
                id = "order1",
                userId = "user123",
                restaurantId = "restaurant456",
                restaurantName = "Burger king",
                restaurantImageUrl = "",
                meals = listOf(
                    Order.Meal(
                        mealId = "meal1",
                        mealName = "Cheeseburger",
                        quantity = 2
                    ),
                    Order.Meal(
                        mealId = "meal2",
                        mealName = "Fries",
                        quantity = 1
                    )
                ),
                totalPrice = 15.99,
                createdAt = Clock.System.now().epochSeconds,
                orderStatus = 4,
                timeToArriveInMints = 20
            ),
            Order(
                id = "order1",
                userId = "user123",
                restaurantId = "restaurant456",
                restaurantName = "Burger king",
                restaurantImageUrl = "",
                meals = listOf(
                    Order.Meal(
                        mealId = "meal1",
                        mealName = "Cheeseburger",
                        quantity = 2
                    ),
                    Order.Meal(
                        mealId = "meal2",
                        mealName = "Fries",
                        quantity = 1
                    )
                ),
                totalPrice = 15.99,
                createdAt = Clock.System.now().epochSeconds,
                orderStatus = 4,
                timeToArriveInMints = 20
            ),
            Order(
                id = "order1",
                userId = "user123",
                restaurantId = "restaurant456",
                restaurantName = "Burger king",
                restaurantImageUrl = "",
                meals = listOf(
                    Order.Meal(
                        mealId = "meal1",
                        mealName = "Cheeseburger",
                        quantity = 2
                    ),
                    Order.Meal(
                        mealId = "meal2",
                        mealName = "Fries",
                        quantity = 1
                    )
                ),
                totalPrice = 15.99,
                createdAt = Clock.System.now().epochSeconds,
                orderStatus = 4,
                timeToArriveInMints = 20
            ),
        )
    }

}