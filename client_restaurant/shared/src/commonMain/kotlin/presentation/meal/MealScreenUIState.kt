package presentation.meal

data class MealScreenUIState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val currency: String = "USD",
    val isAddEnable: Boolean = false,
    val cuisines: List<CuisineUIState> = emptyList()
)


data class CuisineUIState(
    val id: String = "",
    val name: String = ""
)

fun List<CuisineUIState>.toCuisinesString() = this.joinToString(", ") { it.name }


fun MealScreenUIState.isValid(): Boolean {
   return name.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() //&& cuisines.size in 1..3
}