package domain.entity

data class Meal(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val currency: String,
    val restaurantId: String,
    val cuisines: List<String>,
    val image: String
)
