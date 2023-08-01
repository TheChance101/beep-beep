package org.thechance.service_identity.utils

import com.mongodb.client.result.UpdateResult

fun UpdateResult.isDocumentModified(): Boolean {
    return this.modifiedCount > 0 && this.wasAcknowledged()
}