package org.thechance.service_identity.api

import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse<T>(
    val value: T?,
    val isSuccess: Boolean = true,
    val status: ResponseStatus,

    ) {

    companion object {

        fun error(errorMessage: String, code: Int): ServerResponse<String> {
            return ServerResponse(
                value = "",
                isSuccess = false,
                status = ResponseStatus(message = errorMessage, code = code)
            )
        }

        fun <T> success(result: T, successMessage: String? = null, code: Int = 200): ServerResponse<T> {
            return ServerResponse(
                value = result,
                isSuccess = true,
                status = ResponseStatus(message = successMessage, code = code),
            )
        }

    }

    @Serializable
    data class ResponseStatus(
        val message: String?,
        val code: Int?
    )

}
