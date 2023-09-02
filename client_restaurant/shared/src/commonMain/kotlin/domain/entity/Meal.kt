package domain.entity

data class Meal(
    val id: String,
    val restaurantId: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val price: Double,
    val cuisines: List<String>
)


data class MealAddition(
    val name: String,
    val image: ByteArray?,
    val description: String,
    val price: Double,
    val cuisines: List<String>
)
