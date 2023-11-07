package domain.entity

enum class TripStatus(val statusCode: Int) {
    PENDING(0),
    APPROVED(1),
    RECEIVED(2),
    FINISHED(3);

    companion object {
        fun getTripStatus(statusCode: Int): TripStatus {
            TripStatus.values().forEach {
                if (it.statusCode == statusCode) {
                    return it
                }
            }
            return APPROVED
        }
    }
}