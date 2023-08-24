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
