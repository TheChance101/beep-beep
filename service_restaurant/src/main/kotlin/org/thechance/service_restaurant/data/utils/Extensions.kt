package org.thechance.service_restaurant.data.utils

import com.mongodb.client.result.UpdateResult
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineFindPublisher
import org.thechance.service_restaurant.domain.utils.Validation.Companion.NULL_DOUBLE
import java.util.*
import kotlin.reflect.full.memberProperties

fun <T : Any> CoroutineFindPublisher<T>.paginate(page: Int, limit: Int) = this.skip((page - 1) * limit).limit(limit)

fun List<String>.toUUIDs() = map { UUID.fromString(it) }

fun UpdateResult.isSuccessfullyUpdated(): Boolean {
    return this.modifiedCount > 0L
}


fun getNonEmptyFieldsMap(obj: Any): MutableMap<String, Any> {
    val properties = obj::class.memberProperties
    val updateFields = mutableMapOf<String, Any>()

    for (property in properties) {
        val value = property.getter.call(obj)
        if (value is String && value.isNotEmpty()) {
            updateFields[property.name] = value
        } else if (value is Double && value != NULL_DOUBLE) {
            updateFields[property.name] = value
        } else if (value is List<*> && value.isNotEmpty()) {
            updateFields[property.name] = value
        }
    }

    return updateFields
}