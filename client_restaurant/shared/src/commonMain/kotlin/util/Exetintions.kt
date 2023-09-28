package util

import kotlinx.datetime.LocalDateTime

fun LocalDateTime.formatDateTime(): String = buildString {
    append(month.name.take(3).capitalizeFirstLetter())
    append(' ')
    append(dayOfMonth)
    append('/')
    append(year)
    append(" - ")
    append(hour)
    append(':')
    append(minute.toString().padStart(2, '0'))
}

fun String.capitalizeFirstLetter(): String {
    return lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}


fun List<String>.toWeekDay():List<String>{
    val weekdays = mapOf(
        "1" to "Mon",
        "2" to "Tue",
        "3" to "Wed",
        "4" to "Thur",
        "5" to "Fri",
        "6" to "Sat",
        "7" to "Sun"
    )
    return this.map { weekdays[it] ?: "Invalid Day" }
}
