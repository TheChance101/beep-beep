package org.thechance.service_taxi.data.utils

import com.mongodb.client.model.Updates
import com.mongodb.client.result.UpdateResult
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineFindPublisher
import kotlin.reflect.full.memberProperties

fun <T : Any> CoroutineFindPublisher<T>.paginate(page: Int, limit: Int) =
    skip((page - 1) * limit).limit(limit)

fun UpdateResult.isSuccessfullyUpdated() = modifiedCount > 0L

fun Any?.isNotNull() = this == null

fun <T> updateNotNullProperties(
    obj: T,
    filter: (propertyName: String) -> Boolean = { true },
    foreach: (propertyName: String, value: Any) -> Bson = { n, v -> Updates.set(n, v) }
): Bson {
    return Updates.combine(
        obj!!::class.memberProperties
            .filter { it.getter.call(obj) != null && filter(it.name) }
            .map { foreach(it.name, it.getter.call(obj)!!) }
    )
}

fun List<String>.toObjectIds() = map { ObjectId(it) }