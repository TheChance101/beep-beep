package presentation.main

sealed class MainScreenUIEffect {
    object Back : MainScreenUIEffect()
    data class NavigateToAllMeals(val restaurantId: String) : MainScreenUIEffect()
    data class NavigateToRestaurantInfo(val restaurantId: String) : MainScreenUIEffect()
    data class NavigateToOrders(val restaurantId: String) : MainScreenUIEffect()
    data class NavigateToOrdersHistory(val restaurantId: String) : MainScreenUIEffect()
}
