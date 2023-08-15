package org.thechance.service_identity.data.collection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PermissionCollection(
    @SerialName("_id")
    val _id: Int,
    @SerialName("permission")
    val permission: String,
    @SerialName("is_deleted")
    val isDeleted: Boolean = false
)
