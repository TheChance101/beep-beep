package data.local.mapper

import domain.entity.Cost
import domain.entity.PreferredRide
import domain.entity.RideQuality

fun PreferredRide.toFormattedString(): String {
    val costStr = when (cost) {
        Cost.LOW -> "low"
        Cost.HIGH -> "high"
    }

    val qualityStr = when (quality) {
        RideQuality.LOW -> "low"
        RideQuality.HIGH -> "high"
    }

    return "$costStr,$qualityStr"
}

fun String.toPreferredRide(): PreferredRide {
    val parts = split(',')
    val cost = when (parts[0]) {
        "low" -> Cost.LOW
        "high" -> Cost.HIGH
        else -> Cost.LOW
    }

    val quality = when (parts[1]) {
        "low" -> RideQuality.LOW
        "high" -> RideQuality.HIGH
        else -> RideQuality.LOW
    }

    return PreferredRide(cost, quality)
}
