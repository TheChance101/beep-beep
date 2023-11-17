package domain.entity

enum class OrderStatus(val key: Int) {
    PENDING(0),
    APPROVED(1),
    IN_COOKING(2),
    DONE(3),
    CANCELED(4);


    companion object {
        fun getOrderStatus(statusCode: Int): OrderStatus {
            OrderStatus.values().forEach {
                if (it.key == statusCode) {
                    return it
                }
            }
            return PENDING
        }
    }
}