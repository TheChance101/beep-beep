package presentation.resturantDetails

import domain.entity.Meal
import domain.entity.PriceLevel
import domain.entity.Restaurant
import presentation.base.ErrorState
import presentation.composable.ModalBottomSheetState

data class RestaurantUIState(
    val restaurantInfo: RestaurantInfoUIState = RestaurantInfoUIState(),
    val isFavourite: Boolean = false,
    val mostOrders :List<MealUIState> = emptyList(),
    val sweets :List<MealUIState> = emptyList(),
    val isLoading: Boolean = false,
    val error: ErrorState? = null,
    val isLogin :Boolean = false,
    val sheetState: ModalBottomSheetState = ModalBottomSheetState(),
    val showSheet: Boolean = false,
)

data class MealUIState(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val image: String = "",
    val discount: Double = 0.0,
    val currency: String = "$",
)

fun Meal.toUIState() = MealUIState(
    id = id,
    name = name,
    price = price,
    image = image,
    currency = currency,
)

data class RestaurantInfoUIState(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val rating: Double = 0.0,
    val priceLevel:PriceLevel = PriceLevel.LOW,
    val image: String = "",
    val discount: Int = 0,
    val description: String = "",
)

fun Restaurant.toUIState() = RestaurantInfoUIState(
    id = id ,
    name = name,
    address = address,
    rating = rate,
    priceLevel = priceLevel,
    image = "",
    discount = doubleToPercentage(15.0),
    description = description,
)

fun doubleToPercentage(value: Double): Int {
    return (value.toDouble() / 100.0).toInt()
}