package presentation.PreferredFood

import util.preferredCuisine

data class PreferredFoodUIState(
    val preferredFood : List<FoodUIState> = preferredCuisine,
    val selectedPreferredFood :List<String> = emptyList()
)
data class FoodUIState(
    val name: String = "",
    val image: String = "",
)
