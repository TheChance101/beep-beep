package data.remote.mapper

import domain.entity.Time

fun String?.toTime(): Time {
    if(this != null){
        val parts = this.split(":")

        if (parts.size != 2) {
            throw IllegalArgumentException("Invalid time string format: $this")
        }

        val hours = parts[0].toIntOrNull()
        val minutes = parts[1].toIntOrNull()

        if (hours == null || minutes == null || hours !in 0..23 || minutes !in 0..59) {
            throw IllegalArgumentException("Invalid time values in time string: $this")
        }

        return Time(hours, minutes)
    }
    return Time(0,0)
}