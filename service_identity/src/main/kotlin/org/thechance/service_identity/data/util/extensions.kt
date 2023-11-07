package org.thechance.service_identity.data.util

import com.mongodb.client.result.UpdateResult
import org.litote.kmongo.coroutine.CoroutineFindPublisher
import kotlin.reflect.full.memberProperties

fun UpdateResult.isUpdatedSuccessfully(): Boolean {
    return this.modifiedCount > 0
}

fun <T : Any> CoroutineFindPublisher<T>.paginate(page: Int, limit: Int) = this.skip((page - 1) * limit).limit(limit)

fun getNonEmptyFieldsMap(obj: Any): MutableMap<String, Any> {
    val properties = obj::class.memberProperties
    val updateFields = mutableMapOf<String, Any>()

    for (property in properties) {
        val value = property.getter.call(obj)
        if (value is String && value.isNotEmpty()) {
            updateFields[property.name] = value
        } else if (value is Double && value != -1) {
            updateFields[property.name] = value
        } else if (value is List<*> && value.isNotEmpty()) {
            updateFields[property.name] = value
        }
    }

    return updateFields
}