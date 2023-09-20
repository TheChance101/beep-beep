package org.thechance.service_restaurant.domain.entity

import kotlinx.datetime.LocalDateTime

data class Order(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val meals: List<Meal>,
    val totalPrice: Double,
    val createdAt: LocalDateTime,
    val status: Status
) {

    data class Meal(
        val meadId: String,
        val quantity: Int
    )

    enum class Status(val statusCode: Int) {
        PENDING(0),
        COOKING(1),
        APPROVED(2),
        CANCELED(3),
        DONE(4);

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
