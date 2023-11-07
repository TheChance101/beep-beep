package util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun String.capitalizeFirstLetter(): String {
    return lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}


fun Long.convertLongToFormattedDate(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val monthAbbreviation = localDateTime.month.name.substring(0, 3)
    val formattedDate = "${monthAbbreviation} ${localDateTime.dayOfMonth}/${localDateTime.year}"
    return formattedDate
}

