package data.gateway.fake

import domain.entity.Cart
import domain.entity.DeliveryRide
import domain.entity.FoodOrder
import domain.entity.Location
import domain.entity.TaxiColor
import domain.entity.TaxiRide
import domain.entity.Trip
import domain.entity.TripStatus
import domain.gateway.ITransactionsGateway
import kotlinx.coroutines.flow.Flow
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
                taxiColor = TaxiColor.getColorByColorNumber(4294639360L),
                rate = 3.5,
                price = 25.0,
                startDate = "2023-10-17",
                isATaxiTrip = true,
                tripStatus = TripStatus.RECEIVED,
                timeToArriveInMints = 10
            ),
            Trip(
                id = "123456",
                clientId = "67890",
                startPoint = Location(37.7749, -122.4194),
                destination = Location(34.0522, -118.2437),
                startPointAddress = "123 Main Street, City",
                destinationAddress = "456 Elm Street, Town",
                taxiColor = TaxiColor.getColorByColorNumber(4294639360L),
                rate = 3.5,
                price = 25.0,
                startDate = "2023-10-17",
                isATaxiTrip = true,
                tripStatus = TripStatus.RECEIVED,
                timeToArriveInMints = 10
            ),
            Trip(
                id = "123456",
                clientId = "67890",
                startPoint = Location(37.7749, -122.4194),
                destination = Location(34.0522, -118.2437),
                startPointAddress = "123 Main Street, City",
                destinationAddress = "456 Elm Street, Town",
                taxiColor = TaxiColor.getColorByColorNumber(4294639360L),
                rate = 3.5,
                price = 25.0,
                isATaxiTrip = true,
                tripStatus = TripStatus.RECEIVED,
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
                createdAt = "24 Aug 2021",
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
                createdAt = "23 Sep 2021",
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
                createdAt = "11 Oct 2021",
                orderStatus = FoodOrder.OrderStatusInRestaurant.APPROVED,
                orderEstimatedTime = LocalTime(0, 30).minute,
            )
        )
    }

    override suspend fun getCart(): Cart {
        TODO("Not yet implemented")
    }

    override suspend fun orderNow(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateCart(cart: Cart) {
        TODO("Not yet implemented")
    }

    override suspend fun getActiveTaxiTrips(): List<Trip> {
        return emptyList()
    }

    override suspend fun getActiveDeliveryTrips(): List<DeliveryRide> {
        TODO("Not yet implemented")
    }

    override suspend fun getActiveOrders(): List<FoodOrder> {
        TODO("Not yet implemented")
    }

    override suspend fun trackTaxiRide(tripId: String): Flow<TaxiRide> {
        TODO("Not yet implemented")
    }

    override suspend fun trackDeliveryRide(tripId: String): Flow<DeliveryRide> {
        TODO("Not yet implemented")
    }

    override suspend fun trackFoodOrderInRestaurant(orderId: String): Flow<FoodOrder> {
        TODO("Not yet implemented")
    }

}
