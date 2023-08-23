package org.thechance.service_identity.data.collection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PermissionCollection(
    @SerialName("_id")
    val id: Int,
    val permission: String,
    val isDeleted: Boolean = false
)
