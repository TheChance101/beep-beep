package presentation.preferredRide

import domain.entity.Cost
import domain.entity.PreferredRide
import domain.entity.RideQuality

data class PreferredRideUiState(
    val cost: Cost = Cost.LOW,
    val quality: RideQuality = RideQuality.LOW
)

fun PreferredRideUiState.toPreferredRide(): PreferredRide {
    return PreferredRide(
        cost = cost,
        quality = quality
    )
}