package org.thechance.api_gateway.data.model

open class ApiGatewayException(message: String) : Throwable(message)

class LocalizedMessageException(message: String) : ApiGatewayException(message)