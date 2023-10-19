package presentation.resturantDetails

import domain.entity.PriceLevel
import presentation.base.ErrorState
import presentation.composable.ModalBottomSheetState

data class RestaurantUIState(
    val restaurantInfo: RestaurantInfoUIState = RestaurantInfoUIState(),
    val isFavourite: Boolean = false,
    val mostOrders: List<MealUIState> = emptyList(),
    val sweets: List<MealUIState> = emptyList(),
    val isLoading: Boolean = false,
    val error: ErrorState? = null,
    val isLogin: Boolean = false,
    val sheetState: ModalBottomSheetState = ModalBottomSheetState(),
    val sheetMealState: ModalBottomSheetState = ModalBottomSheetState(),
    val showLoginSheet: Boolean = false,
    val meal: MealUIState = MealUIState(),
    val showMealSheet: Boolean = false,
    val showToast: Boolean = false,
)

data class MealUIState(
    val id: String = "",
    val restaurantName: String = "",
    val totalPrice: Double = 0.0,
    val name: String = "",
    val price: Double = 0.0,
    val image: String = "",
    val discount: Double = 0.0,
    val currency: String = "",
    val quantity: Int = 1,
    val description: String = "",
)

data class RestaurantInfoUIState(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val rating: Double = 0.0,
    val priceLevel: PriceLevel = PriceLevel.LOW,
    val image: String = "",
    val discount: Int = 0,
    val hasFreeDelivery: Boolean = false,
    val description: String = "",
)
