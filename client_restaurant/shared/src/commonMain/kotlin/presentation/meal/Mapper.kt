package presentation.meal

import domain.entity.Cuisine
import domain.entity.Meal

fun Cuisine.toCuisineUIState(isSelected: Boolean): CuisineUIState {
    return CuisineUIState(
        id = id,
        name = name,
        isSelected = isSelected
    )
}

fun List<Cuisine>.toCuisineUIState(isSelected: Boolean = false) =
    map { it.toCuisineUIState(isSelected) }

fun List<CuisineUIState>.toCuisinesString() = this.joinToString(", ") { it.name }

fun MealUIState.isValid(): Boolean {
    return name.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() //&& cuisines.size in 1..3
}

fun Meal.toMealUIState(): MealUIState {
    return MealUIState(
        id = id,
        name = name,
        description = description,
        price = "$price",
        selectedCuisines = cuisines.toCuisineUIState(true)
    )
}
