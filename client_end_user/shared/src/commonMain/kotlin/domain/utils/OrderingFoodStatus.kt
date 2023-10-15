package domain.utils

sealed class OrderingFoodStatus(statusCode: Int) {
    data class OrderPlaced(val statusCode: Int = 0) : OrderingFoodStatus(statusCode)
    data class OrderInCooking(val statusCode: Int = 1) : OrderingFoodStatus(statusCode)
    data class OrderInTheRoute(val statusCode: Int = 2) : OrderingFoodStatus(statusCode)
    data class OrderArrived(val statusCode: Int = 3) : OrderingFoodStatus(statusCode)
}
