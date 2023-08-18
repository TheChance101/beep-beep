package org.thechance.service_restaurant.domain.utils

enum class OrderStatus(val statusCode: Int) {
    PENDING(0),
    COOKING(1),
    APPROVED(2),
    CANCELED(3),
    DONE(4);

    companion object {
        fun getOrderStatus(statusCode: Int) : OrderStatus {
            OrderStatus.values().forEach { if (it.statusCode == statusCode) { return it } }
            return PENDING
        }
    }
}