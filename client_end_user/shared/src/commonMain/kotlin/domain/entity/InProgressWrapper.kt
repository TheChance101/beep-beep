package domain.entity

data class InProgressWrapper(
    val taxisOnTheWay: List<Taxi> = emptyList(),
    val tripsOnTheWay: List<Trip> = emptyList(),
    val ordersOnTheWay: List<Order> = emptyList(),
)
