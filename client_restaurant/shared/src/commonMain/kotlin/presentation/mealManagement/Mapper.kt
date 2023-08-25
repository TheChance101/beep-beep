package presentation.mealManagement

import domain.entity.Cuisine
import domain.entity.Meal
import domain.entity.MealAddition

fun Cuisine.toUIState(): CuisineUIState {
    return CuisineUIState(
        id = id,
        name = name,
        isSelected = false
    )
}

fun List<Cuisine>.toUIState() = map { it.toUIState() }

fun List<CuisineUIState>.toCuisinesString() = joinToString(", ", transform = CuisineUIState::name)


fun MealDetails.isValid(): Boolean {
    return name.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() && mealCuisines.size in 1..3
}

fun CuisineUIState.toCuisineEntity() = Cuisine(id = id, name = name)
fun List<CuisineUIState>.toCuisineEntity() = map { it.toCuisineEntity() }


fun MealDetails.toMealAddition() = MealAddition(
    name = name,
    image = image,
    description = description,
    price = price.toDouble(),
    cuisines = mealCuisines.map { Cuisine(id = it.id, name = name) }
)

fun Meal.toUIState() = MealDetails(
    name = name,
    description = description,
    imageUrl = imageUrl,
    price = "$price",
    mealCuisines = cuisines.toUIState()
)
