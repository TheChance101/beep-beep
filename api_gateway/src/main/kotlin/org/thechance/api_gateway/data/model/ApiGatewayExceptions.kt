package org.thechance.api_gateway.data.model

open class ApiGatewayException(message: String) : Throwable(message)

class LocalizedMessageException(val errorMessages: List<Map<Int, String>>) :
    ApiGatewayException(errorMessages.toString())