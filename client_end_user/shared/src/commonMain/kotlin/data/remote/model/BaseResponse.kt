package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val value: T?,
    val isSuccess: Boolean = true,
    val status: ResponseStatus
)

@Serializable
data class ResponseStatus(
    val errorMessages: List<Map<Int, String>>? = null,
    val successMessage: String? = null,
    val code: Int?
)