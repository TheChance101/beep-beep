package org.thechance.service_restaurant.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExploreRestaurantDto (
    @SerialName("restaurants") val restaurants :List<RestaurantDto>,
    @SerialName("meals") val meals :List<MealDto>,
)