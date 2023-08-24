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

fun List<CuisineUIState>.toCuisinesString() = joinToString(", ", transform = CuisineUIState::name)


fun MealUIState.isValid(): Boolean {
    return name.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() && selectedCuisines.size in 1..3
}

fun Meal.toMealUIState(): MealUIState {
    return MealUIState(
        id = id,
        name = name,
        description = description,
        price = "$price",
        imageUrl = imageUrl,
        selectedCuisines = cuisines.toCuisineUIState(true)
    )
}


fun MealUIState.toMealEntity() = Meal(
    id = id,
    name = name,
    imageUrl = imageUrl,
    description = description,
    price = price.toDoubleOrNull() ?: 0.0,
    cuisines = selectedCuisines.toCuisineEntity(),
    restaurantId = ""
)


fun CuisineUIState.toCuisineEntity() = Cuisine(id = id, name = name)
fun List<CuisineUIState>.toCuisineEntity() = map { it.toCuisineEntity() }
