package data.remote.model

data class MealDto(
    val id: String,
    val restaurantId: String,
    val name: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val price: Double? = null,
    val cuisines : List<String>? = null
)


