package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BasePaginationResponseDto<T>(
    val total: Long,
    val result: List<T>,
)