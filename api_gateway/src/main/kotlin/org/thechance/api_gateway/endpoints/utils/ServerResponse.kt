package org.thechance.api_gateway.endpoints.utils

@kotlinx.serialization.Serializable
data class ServerResponse<T>(
    val value: T?,
    val isSuccess: Boolean = true,
    val status: ResponseStatus,

    ) {

    companion object {

        fun error(errorMessage: List<String>, code: Int): ServerResponse<String> {
            return ServerResponse(
                value = "",
                isSuccess = false,
                status = ResponseStatus(errorMessages = errorMessage, code = code)
            )
        }

        fun <T> success(result: T, successMessage:String?): ServerResponse<T> {
            return ServerResponse(
                value = result,
                isSuccess = true,
                status = ResponseStatus(successMessage = successMessage, code = 200),
            )
        }
    }

    @kotlinx.serialization.Serializable
    data class ResponseStatus(
        val errorMessages: List<String>? = null,
        val successMessage: String? = null,
        val code: Int?
    )

}