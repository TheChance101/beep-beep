package util

enum class OrderState(val statusCode: Int) {
    PENDING(0),
    IN_COOKING(1),
    CANCELED(2),
    FINISHED(3);
}