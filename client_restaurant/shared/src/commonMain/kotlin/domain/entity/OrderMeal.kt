package domain.entity

data class OrderMeal(
    val id: String,
    val mealImageUrl: String,
    val mealName: String,
    val quantity: Int,
)