package presentation.meal

import domain.entity.Cuisine
import domain.entity.Meal

fun Cuisine.toCuisineUIState(): CuisineUIState {
    return CuisineUIState(
        id = id,
        name = name
    )
}

fun List<Cuisine>.toCuisineUIState() = map { it.toCuisineUIState() }

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
        cuisines = cuisines.toCuisineUIState()
    )
}
