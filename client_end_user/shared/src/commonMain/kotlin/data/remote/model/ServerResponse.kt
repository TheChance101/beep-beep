package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse<T>(
    @SerialName("value")
    val value: T? = null,
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