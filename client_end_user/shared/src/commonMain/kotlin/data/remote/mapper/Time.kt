package data.remote.mapper

import domain.entity.Time

fun Long.toTime(): Time {
    val milliseconds = this
    val seconds = milliseconds / 1000
    val minutes = (seconds / 60).toInt()
    val hours = (minutes / 60).toInt()
    return Time(hours % 24, minutes % 60)
}