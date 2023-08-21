package util

enum class OrderState(val statusCode: Int) {
    PENDING(0),
    COOKING(1),
    APPROVED(2),
    CANCELED(3),
    DONE(4);

    companion object {
        fun getOrderStatus(statusCode: Int): OrderState {
            OrderState.values().forEach {
                if (it.statusCode == statusCode) {
                    return it
                }
            }
            return PENDING
        }
    }
}