package org.thechance.service_notification.data.utils

import com.mongodb.client.result.UpdateResult
import org.litote.kmongo.coroutine.CoroutineFindPublisher

fun UpdateResult.isSuccessfullyUpdated(): Boolean {
    return this.modifiedCount > 0L
}

fun <T : Any> CoroutineFindPublisher<T>.paginate(page: Int, limit: Int) = this.skip((page - 1) * limit).limit(limit)
