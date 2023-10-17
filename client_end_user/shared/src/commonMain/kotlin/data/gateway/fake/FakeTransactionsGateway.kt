package data.gateway.fake

import domain.entity.Cart
import domain.entity.Location
import domain.entity.FoodOrder
import domain.entity.Trip
import domain.gateway.ITransactionsGateway
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class FakeTransactionsGateway : ITransactionsGateway {
    override suspend fun getTripHistory(): List<Trip> {
        return listOf(
            Trip(
                id = "123456",
                clientId = "67890",
                startPoint = Location(37.7749, -122.4194),
                destination = Location(34.0522, -118.2437),
                startPointAddress = "123 Main Street, City",
                destinationAddress = "456 Elm Street, Town",
                rate = 3.5,
                price = 25.0,
                startDate = LocalDate(2023, 10, 17),
                isATaxiTrip = true,
                tripStatus = 1,
                timeToArriveInMints = 10
            ),
            Trip(
                id = "123456",
                clientId = "67890",
                startPoint = Location(37.7749, -122.4194),
                destination = Location(34.0522, -118.2437),
                startPointAddress = "123 Main Street, City",
                destinationAddress = "456 Elm Street, Town",
                rate = 3.5,
                price = 25.0,
                startDate = LocalDate(2023, 10, 17),
                isATaxiTrip = true,
                tripStatus = 1,
                timeToArriveInMints = 10
            ),
            Trip(
                id = "123456",
                clientId = "67890",
                startPoint = Location(37.7749, -122.4194),
                destination = Location(34.0522, -118.2437),
                startPointAddress = "123 Main Street, City",
                destinationAddress = "456 Elm Street, Town",
                rate = 3.5,
                price = 25.0,
                startDate = LocalDate(2023, 10, 17),
                isATaxiTrip = true,
                tripStatus = 1,
                timeToArriveInMints = 10
            ),
        )
    }

    override suspend fun getOrderHistoryGateway(): List<FoodOrder> {
        return listOf(
            FoodOrder(
                id = "order2",
                userId = "user123",
                restaurantId = "restaurant789",
                restaurantImageUrl = "https://www.freshnlean.com/wp-content/uploads/2021/03/Meal-Plan-plate-protein.png",
                restaurantName = "Zeko Talawoth",
                currency = "$",
                meals = listOf(),
                totalPrice = 500.50,
                createdAt = 564844874874L,
                orderStatus = FoodOrder.OrderStatusInRestaurant.APPROVED,
                orderEstimatedTime = LocalTime(0, 30).minute,
            ),
            FoodOrder(
                id = "order2",
                userId = "user123",
                restaurantId = "restaurant789",
                restaurantImageUrl = "https://www.freshnlean.com/wp-content/uploads/2021/03/Meal-Plan-plate-protein.png",
                restaurantName = "Zeko Talawoth",
                currency = "$",
                meals = listOf(),
                totalPrice = 500.50,
                createdAt = 564844874874L,
                orderStatus = FoodOrder.OrderStatusInRestaurant.APPROVED,
                orderEstimatedTime = LocalTime(0, 30).minute,
            ),
            FoodOrder(
                id = "order2",
                userId = "user123",
                restaurantId = "restaurant789",
                restaurantImageUrl = "https://www.freshnlean.com/wp-content/uploads/2021/03/Meal-Plan-plate-protein.png",
                restaurantName = "Zeko Talawoth",
                currency = "$",
                meals = listOf(),
                totalPrice = 500.50,
                createdAt = 564844874874L,
                orderStatus = FoodOrder.OrderStatusInRestaurant.APPROVED,
                orderEstimatedTime = LocalTime(0, 30).minute,
            )
        )
    }

    override suspend fun getCart(): Cart {
        TODO("Not yet implemented")
    }

    override suspend fun getActiveTrips(): List<Trip> {
        return emptyList()
    }

    override suspend fun getActiveOrders(): List<FoodOrder> {
        TODO("Not yet implemented")
    }

}
