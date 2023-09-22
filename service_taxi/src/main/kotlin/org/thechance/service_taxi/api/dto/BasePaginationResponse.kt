package org.thechance.service_taxi.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class BasePaginationResponse<T>(
    val total: Long,
    val items: List<T>
)