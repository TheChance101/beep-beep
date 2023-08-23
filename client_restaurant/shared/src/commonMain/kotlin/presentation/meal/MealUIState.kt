package presentation.meal

data class MealUIState(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val currency: String = "USD",
    val flag: String = "",
    val image: ByteArray? = null,
    val imageUrl: String = "",
    val isAddEnable: Boolean = false,
    val selectedCuisines: List<CuisineUIState> = emptyList(),
    val cuisines: List<CuisineUIState> = emptyList()
)


data class CuisineUIState(
    val id: String = "",
    val name: String = "",
    val isSelected: Boolean = false
)

