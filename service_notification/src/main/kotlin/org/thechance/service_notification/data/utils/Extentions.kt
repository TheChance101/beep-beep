package org.thechance.service_notification.data.utils

import com.mongodb.client.result.UpdateResult
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.litote.kmongo.coroutine.CoroutineFindPublisher

fun UpdateResult.isSuccessfullyUpdated(): Boolean {
    return this.modifiedCount > 0L
}

fun <T : Any> CoroutineFindPublisher<T>.paginate(page: Int, limit: Int) = this.skip((page - 1) * limit).limit(limit)


fun Long.toDate(): Date {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.UTC)

    val day = localDateTime.dayOfMonth
    val month = localDateTime.month
    val year = localDateTime.year

    return Date(day, month, year)
}