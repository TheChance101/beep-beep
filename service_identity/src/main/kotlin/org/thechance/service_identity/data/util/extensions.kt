package org.thechance.service_identity.data.util

import com.mongodb.client.result.UpdateResult

fun UpdateResult.isUpdatedSuccessfully(): Boolean {
    return this.modifiedCount > 0
}