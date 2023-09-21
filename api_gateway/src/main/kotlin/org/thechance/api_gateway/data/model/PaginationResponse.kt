package org.thechance.api_gateway.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PaginationResponse<T>(
    val total: Long,
    val items: List<T>
)