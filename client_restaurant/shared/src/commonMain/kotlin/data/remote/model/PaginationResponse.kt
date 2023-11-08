package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationResponse<T>(
    @SerialName("items")
    val items: List<T>,
    @SerialName("page")
    val page: Int,
    @SerialName("total")
    val total: Long
)