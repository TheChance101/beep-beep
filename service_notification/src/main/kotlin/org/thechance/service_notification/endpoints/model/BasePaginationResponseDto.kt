package org.thechance.service_notification.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class BasePaginationResponseDto<T>(
    val items: List<T>,
    val page: Int,
    val total: Long
)