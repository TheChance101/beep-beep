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