package org.thechance.service_restaurant.utils

import com.mongodb.client.result.UpdateResult

fun UpdateResult.isSuccessfullyUpdated(): Boolean {
    return this.modifiedCount > 0L
}

