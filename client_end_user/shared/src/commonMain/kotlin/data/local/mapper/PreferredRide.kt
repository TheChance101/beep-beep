package data.local.mapper

import domain.entity.Cost
import domain.entity.PreferredRide
import domain.entity.RideQuality

fun PreferredRide.toFormattedString(): String {
    val qualityStr = when (quality) {
        RideQuality.LOW -> "low"
        RideQuality.HIGH -> "high"
    }

    return qualityStr
}

fun String.toPreferredRide(): PreferredRide {
    val cost = when (this) {
        "low" -> Cost.LOW
        "high" -> Cost.HIGH
        else -> Cost.LOW
    }

    val quality = when (this) {
        "low" -> RideQuality.LOW
        "high" -> RideQuality.HIGH
        else -> RideQuality.LOW
    }

    return PreferredRide(cost, quality)
}
