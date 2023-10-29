package domain.entity

data class Offer(
    val id: String,
    val title: String,
    val imageUrl: String,
    val restaurants: List<Restaurant>
)
