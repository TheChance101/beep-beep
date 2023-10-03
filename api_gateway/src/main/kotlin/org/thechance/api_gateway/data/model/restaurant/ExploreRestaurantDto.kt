package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExploreRestaurantDto (
    @SerialName("restaurants") val restaurants :List<RestaurantDto>,
    @SerialName("meals") val meals :List<MealDto>,
)