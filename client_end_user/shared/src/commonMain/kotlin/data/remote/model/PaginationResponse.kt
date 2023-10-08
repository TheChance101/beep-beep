package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PaginationResponse<T>(
    val items: List<T>,
    val page: Int,
    val total: Long
)