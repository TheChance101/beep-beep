package org.thechance.common.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse<T>(
    val value: T?,
    val isSuccess: Boolean = true,
    val status: ResponseStatus
)

@Serializable
data class ResponseStatus(
    val errorMessages: Map<String, String>? = null,
    val successMessage: String? = null,
    val code: Int?
)