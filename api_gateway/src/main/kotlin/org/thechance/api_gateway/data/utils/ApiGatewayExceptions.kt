package org.thechance.api_gateway.data.utils

open class ApiGatewayException(message: String) : Throwable(message)

class LocalizedMessageException(val errorMessages: Map<Int, String>) :
    ApiGatewayException(errorMessages.toString())