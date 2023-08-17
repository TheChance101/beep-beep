package org.thechance.api_gateway.endpoints.utils

@kotlinx.serialization.Serializable
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

        fun <T> success(result: T,successMessage: String? = null): ServerResponse<T> {
            return ServerResponse(
                value = result,
                isSuccess = true,
                status = ResponseStatus(message = successMessage, code = 200),
            )
        }
    }

    @kotlinx.serialization.Serializable
    data class ResponseStatus(
        val message: String?,
        val code: Int?
    )

}