package domain.entity

data class Cart(
    val meals: List<MealCart>,
    val price: Price
)
