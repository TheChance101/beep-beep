package org.thechance.api_gateway.data.model.identity

import kotlinx.serialization.Serializable

/**
 * Created by Aziza Helmy on 8/14/2023.
 */
@Serializable
data class PermissionResource(
    val id: Int,
    val permission: String,
)