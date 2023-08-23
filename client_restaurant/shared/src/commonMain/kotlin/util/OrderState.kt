package util

enum class OrderState(val statusCode: Int) {
    PENDING(0),
    IN_COOKING(1),
    CANCELED(3),
    FINISHED(4);
}