package presentation.mealManagement

import domain.entity.Cuisine
import domain.entity.Meal
import domain.entity.MealModification

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
    return name.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() && mealCuisines.size in 1..10
}

fun CuisineUIState.toCuisineEntity() = Cuisine(id = id, name = name)
fun List<CuisineUIState>.toCuisineEntity() = map { it.toCuisineEntity() }


fun MealDetails.toMealAddition(restaurantId: String) = MealModification(
    name = name,
    description = description,
    price = price.toDouble(),
    cuisines = mealCuisines.map { it.id },
    restaurantId = restaurantId
)

fun Meal.toUIState() = MealDetails(
    restaurantId =restaurantId,
    name = name,
    description = description,
    imageUrl = imageUrl,
    price = "$price",
    mealCuisines = cuisines.toUIState()
)

fun MealDetails.toMealUpdate(mealId: String) = MealModification(
    name = name,
    description = description,
    price = price.toDouble(),
    cuisines = mealCuisines.toCuisineEntity().map { it.id },
    restaurantId = restaurantId,
    id = mealId
)