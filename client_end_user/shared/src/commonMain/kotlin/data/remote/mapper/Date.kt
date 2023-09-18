package data.remote.mapper

import domain.entity.Date
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.toDate(): Date {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.UTC)

    val day = localDateTime.dayOfMonth
    val month = localDateTime.monthNumber
    val year = localDateTime.year

    return Date(day, month, year)
}