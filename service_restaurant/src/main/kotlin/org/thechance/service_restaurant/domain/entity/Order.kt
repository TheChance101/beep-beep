package org.thechance.service_restaurant.domain.entity

import kotlinx.datetime.LocalDateTime

data class Order(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val restaurantName: String,
    val restaurantImage: String,
    val meals: List<OrderedMeal>,
    val totalPrice: Double,
    val currency: String,
    val createdAt: LocalDateTime,
    val status: Status
) {
    enum class Status(val statusCode: Int) {
        PENDING(0),
        APPROVED(1),
        IN_COOKING(2),
        DONE(3),
        CANCELED(4);


        companion object {
            fun getOrderStatus(statusCode: Int): Status {
                Status.values().forEach {
                    if (it.statusCode == statusCode) {
                        return it
                    }
                }
                return PENDING
            }
        }
    }
}
