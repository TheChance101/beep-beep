package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("value")
    val value: T?,
    @SerialName("isSuccess")
    val isSuccess: Boolean = true,
    @SerialName("status")
    val status: ResponseStatus
)

@Serializable
data class ResponseStatus(
    @SerialName("errorMessages")
    val errorMessages: Map<String, String>? = null,
    @SerialName("successMessage")
    val successMessage: String? = null,
    @SerialName("code")
    val code: Int?
)
@Serializable
data class PaginationResponse<T>(
    @SerialName("items")
    val items: List<T>,
    @SerialName("page")
    val page: Int,
    @SerialName("total")
    val total: Long
)