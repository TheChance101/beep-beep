package domain.entity

data class PreferredRide(
    val cost: Cost = Cost.LOW,
    val quality: RideQuality = RideQuality.LOW
)

enum class Cost {
    LOW,
    HIGH
}

enum class RideQuality {
    LOW,
    HIGH
}