package presentation.mealManagement


data class MealDetails(
    val id: String = "",
    val restaurantId : String = "",
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val currency: String = "USD",
    val flag: String = "",
    val image: ByteArray? = null,
    val imageUrl: String = "",
    val mealCuisines: List<CuisineUIState> = emptyList(),
)


data class CuisineUIState(
    val id: String = "",
    val name: String = "",
    val isSelected: Boolean = false,
)

