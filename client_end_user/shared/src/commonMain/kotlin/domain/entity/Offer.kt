package domain.entity

data class Offer(
    val id: String,
    val title: String,
    val restaurants: List<Restaurant>
)
