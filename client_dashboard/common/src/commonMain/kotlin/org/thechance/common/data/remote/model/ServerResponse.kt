package org.thechance.common.data.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse<T>(
    @SerializedName("value")
    val value: T? = null,
    @SerializedName("is_success")
    val isSuccess: Boolean? = null,
    @SerializedName("status")
    val status: ResponseStatus? = null
)

@Serializable
data class ResponseStatus(
    @SerializedName("error_message")
    val errorMessages: Map<String, String>? = null,
    @SerializedName("success_message")
    val successMessage: String? = null,
    @SerializedName("code")
    val code: Int? = null
)