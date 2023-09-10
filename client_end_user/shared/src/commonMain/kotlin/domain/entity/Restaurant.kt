package domain.entity

data class Restaurant(
    val id: String,
    val ownerId: String,
    val ownerUsername: String,
    val name: String,
    val description: String,
    val priceLevel: PriceLevel,
    val rate: Double,
    val phone: String,
    val openingTime: Time,
    val closingTime: Time,
    val location: Location,
    val address: String
)

data class Time(
    val hours: Int,
    val minutes: Int
) {
    init {
        require(hours in 0..23)
        require(minutes in 0..59)
    }
}

enum class PriceLevel(val priceLevel: String) {
    LOW("$"),
    MEDIUM("$$"),
    HIGH("$$$");

    companion object {
        fun getPriceLevel(priceLevel: String): PriceLevel? {
            PriceLevel.values().forEach {
                if (it.priceLevel == priceLevel) {
                    return it
                }
            }
            return null
        }
    }
}
