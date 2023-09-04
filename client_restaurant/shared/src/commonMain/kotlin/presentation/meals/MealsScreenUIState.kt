package presentation.meals

import domain.entity.Cuisine
import domain.entity.Meal
import presentation.base.ErrorState

data class MealsScreenUIState(
    val cuisines: List<CuisineUIState> = emptyList(),
    val meals: List<MealUIState> = emptyList(),
    val selectedCuisine: CuisineUIState = CuisineUIState(),
    val isLoading: Boolean = false,
    val error: ErrorState? = null,
)

data class CuisineUIState(
    val id: String = "",
    val name: String = "",
)

fun Cuisine.toMealUIState(): CuisineUIState {
    return CuisineUIState(
        id = id,
        name = name,
    )
}

fun List<Cuisine>.toCuisineUIState(): List<CuisineUIState> {
    val cuisines = mutableListOf<CuisineUIState>()
    cuisines.add(CuisineUIState("", "All"))
    cuisines.addAll(map { it.toMealUIState() })
    return cuisines.toList()
}

data class MealUIState(
    val id: String,
    val name: String,
    val price: String,
    val imageUrl: String,
)

fun Meal.toMealUIState(): MealUIState {
    return MealUIState(
        id = id,
        name = name,
        price = "\$ $price",
        imageUrl = "https://static.toiimg.com/thumb/84784534.cms?imgsize=468021&width=800&height=800",
    )
}

fun List<Meal>.toMealUIState(): List<MealUIState> = map { it.toMealUIState() }



