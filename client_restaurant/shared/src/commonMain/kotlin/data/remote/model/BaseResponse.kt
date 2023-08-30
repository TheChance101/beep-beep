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
    @SerialName("id")
    val errorMessages: Map<String, String>? = null,
    @SerialName("id")
    val successMessage: String? = null,
    @SerialName("id")
    val code: Int?
)