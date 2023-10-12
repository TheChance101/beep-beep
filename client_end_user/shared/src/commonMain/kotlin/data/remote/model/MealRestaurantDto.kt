package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealRestaurantDto(
    @SerialName("restaurants")
    val restaurants: List<RestaurantDto>,
    @SerialName("meals")
    val meals: List<MealDto>
)
