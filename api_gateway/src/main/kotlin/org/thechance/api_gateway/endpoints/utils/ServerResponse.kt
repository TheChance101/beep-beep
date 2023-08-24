package org.thechance.api_gateway.endpoints.utils

import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse<T>(
        val value: T?,
        val isSuccess: Boolean = true,
        val status: ResponseStatus,

        ) {

    companion object {

        fun error(errorMessage: Map<Int, String>?, code: Int): ServerResponse<String> {
            return ServerResponse(
                value = "",
                isSuccess = false,
                status = ResponseStatus(errorMessages = errorMessage, code = code)
            )
        }

        inline fun <reified T> success(result: T, successMessage: String?): ServerResponse<T> {
            return ServerResponse(
                value = result,
                isSuccess = true,
                status = ResponseStatus(successMessage = successMessage, code = 200),
            )
        }
    }

    @Serializable
    data class ResponseStatus(
        val errorMessages: Map<Int, String>? = null,
        val successMessage: String? = null,
        val code: Int?
    )

}