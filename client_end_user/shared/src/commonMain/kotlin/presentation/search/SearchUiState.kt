package presentation.search

import domain.entity.Meal
import domain.entity.Restaurant
import presentation.base.ErrorState
import presentation.composable.ModalBottomSheetState
import presentation.resturantDetails.MealUIState


data class SearchUiState(
    val query: String = "",
    val meals: List<MealUIState> = emptyList(),
    val restaurants: List<RestaurantUiState> = emptyList(),
    val showMealSheet: Boolean = false,
    val selectedMeal: MealUIState = MealUIState(),

    val isAddToCartLoading: Boolean = false,
    val isLogin: Boolean = false,
    val showLoginSheet: Boolean = false,
    val showToast: Boolean = false,
    val errorAddToCart: ErrorState? = null,
    val error: ErrorState? = null,
)

data class RestaurantUiState(
    val id: String = "",
    val image: String = "",
    val name: String = "",
)

//Mapper
fun Restaurant.toExploreUiState() = RestaurantUiState(
    id = id,
    name = name,
    image = image
)

fun List<Restaurant>.toExploreUiState() = map { it.toExploreUiState() }
