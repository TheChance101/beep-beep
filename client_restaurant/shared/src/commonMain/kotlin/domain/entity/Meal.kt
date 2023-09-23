package domain.entity

data class Meal(
    val id: String,
    val restaurantId: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val price: Double,
    val cuisines: List<Cuisine>
)


data class MealModification(
    val name: String,
    val description: String,
    val price: Double,
    val cuisines: List<String>,
    val restaurantId: String,
    val id: String? = "",
)