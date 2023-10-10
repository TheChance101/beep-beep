package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class BasePagingResponse(
    val items: List<UserManagementDto>,
    val page: Int,
    val total: Long,
)