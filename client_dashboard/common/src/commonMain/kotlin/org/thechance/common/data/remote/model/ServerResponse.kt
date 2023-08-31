package org.thechance.common.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse<T>(
    @SerialName("value")
    val value: T? = null,
    @SerialName("is_success")
    val isSuccess: Boolean? = null,
    @SerialName("status")
    val status: ResponseStatus? = null
)

@Serializable
data class ResponseStatus(
    @SerialName("error_message")
    val errorMessages: Map<String, String>? = null,
    @SerialName("success_message")
    val successMessage: String? = null,
    @SerialName("code")
    val code: Int? = null
)