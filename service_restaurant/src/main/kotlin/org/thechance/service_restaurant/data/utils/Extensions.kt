package org.thechance.service_restaurant.data.utils

import com.mongodb.client.result.UpdateResult
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineFindPublisher

fun <T : Any> CoroutineFindPublisher<T>.paginate(page: Int, limit: Int) = this.skip((page - 1) * limit).limit(limit)

fun List<String>.toObjectIds() = map { ObjectId(it) }

fun UpdateResult.isSuccessfullyUpdated(): Boolean {
    return this.modifiedCount > 0L
}