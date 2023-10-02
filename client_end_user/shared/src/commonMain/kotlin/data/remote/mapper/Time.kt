package data.remote.mapper

import domain.entity.Time

fun Long.toTime(): Time {
    val milliseconds = this
    val millisecondsPerSecond = 1000L
    val secondsPerMinute = 60L
    val minutesPerHour = 60L
    val hoursPerDay = 24L

    val seconds = milliseconds / millisecondsPerSecond
    val minutes = seconds / secondsPerMinute
    val hours = minutes / minutesPerHour

    return Time((hours % hoursPerDay).toInt(), (minutes % minutesPerHour).toInt())
}

fun String.toTime(): Time {
    val parts = this.split(":")
    if (parts.size == 2) {
        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()
        return Time(hours, minutes)
    } else {
        throw IllegalArgumentException("Invalid Time Format")
    }
}