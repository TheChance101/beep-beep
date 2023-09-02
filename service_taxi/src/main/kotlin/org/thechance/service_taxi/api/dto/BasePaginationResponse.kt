package org.thechance.service_taxi.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class BasePaginationResponse<T>(
    val items: List<T>,
    val total: Long
)