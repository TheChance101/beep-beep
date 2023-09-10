package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InProgressResponse(
    @SerialName("taxisOnTheWay") val taxisOnTheWay: List<TaxiDto>? = null,
    @SerialName("tripsOnTheWay") val tripsOnTheWay: List<TripDto>? = null,
    @SerialName("ordersOnTheWay") val ordersOnTheWay: List<OrderDto>? = null,
)



