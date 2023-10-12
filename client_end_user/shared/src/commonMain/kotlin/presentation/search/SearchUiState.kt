package presentation.search

import domain.entity.Meal
import domain.entity.Restaurant
import presentation.composable.ModalBottomSheetState
import presentation.resturantDetails.MealUIState


data class SearchUiState(
    val query: String = "",
    val meals: List<MealUIState> = emptyList(),
    val restaurants: List<RestaurantUiState> = emptyList(),
    val showMealSheet: Boolean = false,
    val selectedMeal: MealUIState = MealUIState()
)


//data class MealUiState(
//    val id: String = "",
//    val name: String = "",
//    val image: String = "",
//    val restaurantName: String = "",
//    val price: String = ""
//)

data class RestaurantUiState(
    val id: String = "",
    val image: String = "",
    val name: String = ""
)

//Mapper
fun Restaurant.toExploreUiState() = RestaurantUiState(
    id = id,
    name = name,
    image = image
)

fun List<Restaurant>.toExploreUiState() = map { it.toExploreUiState() }