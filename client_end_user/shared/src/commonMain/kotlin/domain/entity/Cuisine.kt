package domain.entity

data class Cuisine(
    val id: String,
    val name: String,
    val imageUrl: String,
    val meals: List<Meal>? = null,
)
