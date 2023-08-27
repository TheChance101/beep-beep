package data.remote.model

import domain.entity.Order
import domain.entity.OrderState
import domain.utils.Constant
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import presentation.base.RequestException

data class OrderDto(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val meals: List<MealDto>,
    val totalPrice: Double? = null,
    val createdAt: String? = null,
    val orderState: Int
) {
    data class MealDto(
        val id: String,
        val mealImageUrl: String,
        val mealName: String,
        val quantity: Int,
    )
}




