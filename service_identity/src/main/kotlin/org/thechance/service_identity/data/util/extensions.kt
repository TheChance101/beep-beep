package org.thechance.service_identity.data.util

import com.mongodb.client.result.UpdateResult
import org.litote.kmongo.coroutine.CoroutineFindPublisher

fun UpdateResult.isUpdatedSuccessfully(): Boolean {
    return this.modifiedCount > 0
}

fun <T : Any> CoroutineFindPublisher<T>.paginate(page: Int, limit: Int) = this.skip((page - 1) * limit).limit(limit)
