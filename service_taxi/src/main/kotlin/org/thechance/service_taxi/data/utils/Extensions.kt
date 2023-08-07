package org.thechance.service_taxi.data.utils

import com.mongodb.client.result.UpdateResult
import org.litote.kmongo.coroutine.CoroutineFindPublisher

fun <T : Any> CoroutineFindPublisher<T>.paginate(page: Int, limit: Int) =
    skip((page - 1) * limit).limit(limit)

fun UpdateResult.isSuccessfullyUpdated(): Boolean {
    return this.modifiedCount > 0L
}