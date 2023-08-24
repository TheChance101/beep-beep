package presentation.meals.state

import domain.entity.Meal

data class  MealUIState(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
)
fun List<Meal>.toUIState(): List<MealUIState> = map { it.toUIState() }
fun Meal.toUIState(): MealUIState {
    return MealUIState(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
    )
}
