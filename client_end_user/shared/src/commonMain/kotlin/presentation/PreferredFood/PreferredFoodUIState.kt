package presentation.PreferredFood

import util.preferredCuisine

data class PreferredFoodUIState(
    val preferredFood : List<FoodUIState> = preferredCuisine,
    val selectedPreferredFood :FoodUIState? = FoodUIState()
)
data class FoodUIState(
    val name: String = "",
    val image: String = "",
)
